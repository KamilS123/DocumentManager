package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private static final Logger log = Logger.getLogger(UserController.class.getName());
    //creating new user and sending to create.jsp
    @RequestMapping("/addUser")
    private String addUser(@RequestParam("registryName") String registryName, @RequestParam("registrySurname") String registrySurname, @RequestParam("registryPassword") String registryPassword, Model model) {
        User user = new User();
        user.setName(registryName);
        user.setSurname(registrySurname);
        user.setPassword(passwordEncoder.encode(registryPassword));
        user.setStatus("user");
        //saving user to database
        userRepository.save(user);

        //adding parameters as attribute to show user details
        model.addAttribute("registryName", registryName);
        model.addAttribute("registrySurname", registrySurname);
        model.addAttribute("registryPassword", registryPassword);
        model.addAttribute("userStatus", "user");
        log.log(Level.INFO, "Add User");
        return "createdUser";
    }

    //from userMainContent changing password byUserName
    @RequestMapping("/changePassword")
    public String changeUserPassword(Principal principal, @RequestParam("password") String password, @RequestParam("repeatPassword") String repeatPassword) {
        if (password.equals(repeatPassword)) {
            String name = principal.getName();
            List<User> userList = (List<User>) userRepository.findAll();
            for (User user : userList) {
                if (user.getName().equals(name)) {
                    userRepository.updatePassword(passwordEncoder.encode(password));
                    log.log(Level.INFO, "password changed ");
                    return "userMainContent";
                }
            }
        } else {
            log.log(Level.INFO, "redirect to changePasswordForm ");
            return "redirect:changePasswordForm";
        }
        return "";
    }

    //from userMainContent by changing status to
    @RequestMapping("/changeStatusToAdmin")
    public String changeStatusToAdmin(Principal principal, HttpServletRequest request) {
        Long id = 0L;
        //getting logged user name
        String name = principal.getName();

        List<User>userList = (List<User>) userRepository.findAll();
        for (User user : userList) {
            if (user.getName().equals(name)) {
                id = user.getId();
                log.log(Level.INFO, "id passed by principal getName() ");
            }
        }
        //passing id from for loop for changing status to admin
        userRepository.updateStatus("admin", id);
        log.log(Level.INFO, "user updated to admin");
        return "userMainContent";
    }

    //from userMainContent input by changing status to moderator to userMainContent
    @RequestMapping("/changeStatusToModerator")
    public String changeStatusToModerator(Principal principal) {
        Long id = 0L;
        //getting logged user name
        String name = principal.getName();
        List<User>userList = (List<User>) userRepository.findAll();
        for (User user : userList) {
            if (user.getName().equals(name)) {
                id = user.getId();
                log.log(Level.INFO, "id passed by principal getName() ");
            }
        }
        //passing id from for loop for changing status to admin
        userRepository.updateStatus("moderator", id);
        log.log(Level.INFO, "user updated to moderator");
        return "moderator/moderatorMainContent";
    }

    //grom UserMainContent by changing status to user. Go to userMainContent
    @RequestMapping("/changeStatusToUser")
    public String changeStatusToUser(Principal principal) {
        //checking cookies. If cookie getName equals userID then pass cookie value to long id
        Long id = 0L;
        //getting logged user name
        String name = principal.getName();

        List<User>userList = (List<User>) userRepository.findAll();
        for (User user : userList) {
            if (user.getName().equals(name)) {
                id = user.getId();
                log.log(Level.INFO, "id passed by principal getName() user ");
            }
        }
        //passing id from for loop for changing status to admin
        userRepository.updateStatus("user", id);
        log.log(Level.INFO, "user updated to user");
        return "userMainContent";
    }

    //from usemMainContent to chengepasswordForm for passing new Password details
    @RequestMapping("/changePasswordForm")
    public String changePasswordForm() {
        log.log(Level.INFO, "sent to change password form");
        return "changePasswordForm";
    }
}

