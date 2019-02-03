package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    //from login sending to registry.jsp for creating new user
    @RequestMapping("/registry")
    public String registration() {
        return "registry";
    }

    //creating new user and sending to create.jsp
    @RequestMapping("/addUser")
    private String addUser(@RequestParam("registryName")String registryName, @RequestParam("registrySurname")String registrySurname, @RequestParam("registryPassword")String registryPassword, Model model) {
        //taking parameters from registry.jsp and creating new User();
        User user = new User();
        user.setName(registryName);
        user.setSurname(registrySurname);
        user.setPassword(registryPassword);
        user.setStatus("user");
        //saving user to database
        userRepository.save(user);

        //adding parameters as attribute
        model.addAttribute("registryName",registryName);
        model.addAttribute("registrySurname",registrySurname);
        model.addAttribute("registryPassword",registryPassword);
        model.addAttribute("userStatus","user");
        return "createdUser";
    }
        //from login checking login data
    @RequestMapping("/checkData")
    public String checkUser(@RequestParam("loginName")String loginName, @RequestParam("loginSurname") String loginSurname, @RequestParam("loginPassword")String loginPassword, Model model) {
        //adding all User to list for checkin user details
        List<User> userList = (List<User>)userRepository.findAll();
        //iterating userList. If name, surname, password equals details from database go to userMainContent else redirect to login
        for(User user: userList) {
            if (user.getName().equals(loginName) && user.getSurname().equals(loginSurname) && user.getPassword().equals(loginPassword)) {
                model.addAttribute("loginName",loginName);
                model.addAttribute("loginSurname",loginSurname);
                model.addAttribute("loginID", user.getId());
                return "userMainContent";
            }
        }
        return "redirect:login";
    }

    //from userMainContent changing password byUserName
    @RequestMapping("/changePassword")
    public String changeUserPassword(@RequestParam("loginID")Long loginID) {
        List<User> userList = (List<User>)userRepository.findAll();
        for(User user : userList) {
            if (user.getId().equals(loginID)) {
                userRepository.updatePassword("noooole",loginID);
                return "userMainContent";
            }
        }
        return "";
    }
}
