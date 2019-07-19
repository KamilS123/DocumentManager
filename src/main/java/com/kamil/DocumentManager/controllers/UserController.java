package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.AdminMessage;
import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.AdminMessageRepository;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import com.kamil.DocumentManager.service.AdminService;
import com.kamil.DocumentManager.service.UserService;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    private static final Logger log = Logger.getLogger(UserController.class.getName());
    //creating new user and sending to create.jsp
    @RequestMapping("/addUser")
    private String addUser(@RequestParam("registryName") String registryName, @RequestParam("registrySurname") String registrySurname, @RequestParam("registryPassword") String registryPassword, Model model) {
        User user = new User();
        user.setName(registryName);
        user.setSurname(registrySurname);
        user.setPassword(passwordEncoder.encode(registryPassword));
        user.setStatus("user");
        user.setActivationStatus("active");
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
       String redirect = userService.changePassword(principal,password,repeatPassword);
        return redirect;
    }

    @RequestMapping("/changeStatus")
    public String changeStatus(@RequestParam("changeStatusValue")String changeStatusValue, Principal principal) {
        Long id = userService.getLoggedUserId(principal);
        String status = "";
        String redirecion = "";
        switch (changeStatusValue) {
            case "user": status = "user";redirecion = "userMainContent";
            break;
            case "moderator": status = "moderator";redirecion = "moderator/moderatorMainContent";
            break;
            case "admin": status = "admin"; redirecion = "admin/adminMainContent";
            break;
        }
        userRepository.updateStatus(status,id);
        log.log(Level.INFO, "user updated to " + status);
        return redirecion;
    }

    //from usemMainContent to chengepasswordForm for passing new Password details
    @RequestMapping("/changePasswordForm")
    public String changePasswordForm() {
        log.log(Level.INFO, "sent to change password form");
        return "changePasswordForm";
    }
    @RequestMapping("/sendMessageToAdminForm")
    public String sendMessageToAdmin() {
        return "sendMessageToAdminForm";
    }
    @RequestMapping("/sendMessageToAdmin")
    public String sendMessageToAdmin(@RequestParam("messageToAdmin")String message, Principal principal) {
        String redirection = userService.checkUserStatus(principal);
        adminService.sendMessage(principal,message);
        return redirection;
    }
}

