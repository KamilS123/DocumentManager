package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.service.AdminService;
import com.kamil.DocumentManager.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class UserController {

    private static final Logger log = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    //creating new user and sending to create.jsp
    @RequestMapping("/addUser")
    private String addUser(@RequestParam("registryName") String registryName, @RequestParam("registrySurname") String registrySurname, @RequestParam("registryPassword") String registryPassword, Model model) {
        userService.saveNewUser(registryName, registrySurname, registryPassword);
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
        log.log(Level.INFO, "changePassword");
        return redirect;
    }

    @RequestMapping("/changeStatus")
    public String changeStatus(@RequestParam("changeStatusValue")String changeStatusValue, Principal principal) {
        log.log(Level.INFO, "user updated to ");
        return userService.changeStatusValue(principal,changeStatusValue);
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
    public String sendMessageToAdmin(@RequestParam("messageToAdmin")String message, Principal principal) throws NotFoundException {
        adminService.sendMessage(message,principal);
        return userService.checkUserStatus(principal);
    }
}

