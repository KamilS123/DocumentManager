package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.Document;
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
public class AdminController {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/adminMainController")
    public String adminMainController(@RequestParam("adminMenuRadio")String adminChoose) {
        String declaredOption = "";
        switch (adminChoose) {
            case "showAllDocuments":
                declaredOption = "showAllDocuments";
                break;
            case "showAllUsers":
                declaredOption = "showAllUsers";
                break;
        }
        return "redirect:" + declaredOption;
    }
    @RequestMapping("/showAllDocuments")
    public String showAllDocuments(Model model) {
        List<Document>documentList = documentRepository.findAll();
        model.addAttribute("docNameToFind",documentList);
        return "admin/adminMainContent";
    }
    @RequestMapping("/showAllUsers")
    public String showAllUsers(Model model) {
        List<User>userList = (List<User>) userRepository.findAll();
        model.addAttribute("userList",userList);
        return "admin/allUsersTable";
    }

}
