package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    //creating new user and sending to create.jsp
    @RequestMapping("/addUser")
    private String addUser(@RequestParam("registryName") String registryName, @RequestParam("registrySurname") String registrySurname, @RequestParam("registryPassword") String registryPassword, Model model) {
        //parameters from registry.jsp and creating new User();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(registryPassword);

        User user = new User();
        user.setName(registryName);
        user.setSurname(registrySurname);
//      user.setPassword(registryPassword);
        user.setPassword(hashedPassword);
        user.setStatus("user");
        //saving user to database
        userRepository.save(user);

        //adding parameters as attribute
        model.addAttribute("registryName", registryName);
        model.addAttribute("registrySurname", registrySurname);
        model.addAttribute("registryPassword", registryPassword);
        model.addAttribute("userStatus", "user");
        log.log(Level.INFO, "Add User");
        return "createdUser";
    }

    //from login checking login data
    @RequestMapping("/checkData")
    public String checkUser(HttpServletResponse response, @RequestParam("loginName") String loginName, @RequestParam("loginSurname") String loginSurname, @RequestParam("loginPassword") String loginPassword, Model model) {
        //adding all User to list for checkin user details
        List<User> userList = (List<User>) userRepository.findAll();
        //iterating userList. If name, surname, password equals details from database go to userMainContent else redirect to login
        for (User user : userList) {
            if (user.getName().equals(loginName) && user.getSurname().equals(loginSurname) && user.getPassword().equals(loginPassword)) {
                model.addAttribute("loginName", loginName);
                model.addAttribute("loginSurname", loginSurname);
                model.addAttribute("loginID", user.getId());
                log.log(Level.INFO, "Check data");
                //set cookie with user id
                Cookie cookieLoginId = new Cookie("userID", String.valueOf(user.getId()));
                cookieLoginId.setMaxAge(60 * 60 * 24);
                response.addCookie(cookieLoginId);
                return "userMainContent";
            }
        }
        log.log(Level.INFO, "Redirect to login");
        return "redirect:login";
    }

    //from userMainContent changing password byUserName
    @RequestMapping("/changePassword")
    public String changeUserPassword(HttpServletRequest request, @RequestParam("password") String password, @RequestParam("repeatPassword") String repeatPassword) {
        if (password.equals(repeatPassword)) {
            Long id = 0L;
            Cookie cookie = null;
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals("userID")) {
                    id = Long.parseLong(cookie.getValue());
                }
            }
            List<User> userList = (List<User>) userRepository.findAll();
            for (User user : userList) {
                if (user.getId().equals(id)) {
                    userRepository.updatePassword(password, id);
                    log.log(Level.INFO, "Change password");
                    return "userMainContent";
                }
            }
        } else {
            return "redirect:changePasswordForm";
        }
        return "";
    }

    //from documentList to userMainContent
    @RequestMapping("/goToMainContent")
    public String goToMainContent() {
        log.log(Level.INFO, "Go to main content");
        return "userMainContent";
    }

    //from userMainContent by changing status to
    @RequestMapping("/changeStatusToAdmin")
    public String changeStatusToAdmin(HttpServletRequest request) {
        Long id = 0L;
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            if (cookie.getName().equals("userID")) {
                id = Long.parseLong(cookie.getValue());
            }
        }
        userRepository.updateStatus("admin", id);
        return "userMainContent";
    }

    //from userMainContent input by changing status to moderator to userMainContent
    @RequestMapping("/changeStatusToModerator")
    public String changeStatusToModerator(HttpServletRequest request) {
        Long id = 0L;
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            if (cookie.getName().equals("userID")) {
                id = Long.parseLong(cookie.getValue());
            }
        }
        userRepository.updateStatus("moderator", id);
        return "userMainContent";
    }

    //grom UserMainContent by changing status to user. Go to userMainContent
    @RequestMapping("/changeStatusToUser")
    public String changeStatusToUser(HttpServletRequest request) {
        //checking cookies. If cookie getName equals userID then pass cookie value to long id
        Long id = 0L;
        Cookie cookie = null;
        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            if (cookie.getName().equals("userID")) {
                id = Long.parseLong(cookie.getValue());
            }
        }
        userRepository.updateStatus("user", id);
        return "userMainContent";
    }

    //from usemMainContent to chengepasswordForm for passing new Password details
    @RequestMapping("/changePasswordForm")
    public String changePasswordForm() {
        return "changePasswordForm";
    }
}

