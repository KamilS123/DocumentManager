package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.AdminMessage;
import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.AdminMessageRepository;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    private AdminMessageRepository adminMessageRepository;

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
        return "allUsersTable";
    }
    @RequestMapping("/displayMessagesFromUsers")
    public String displayMessagesFromUsers(Model model) {
        List<AdminMessage>list = (List<AdminMessage>) adminMessageRepository.findAll();
        list.forEach(System.out::println);
        model.addAttribute("listWithMessagesToAdmin",list);
        return "admin/listWithMessages";
    }
    //grom listWithMessage by input. Delete adminMessage from databese by passed id and resend list with messages
    @RequestMapping("/deleteMessageFromUsers")
    public String deleteMessageFromUsers(@RequestParam("deleteMessageID")Long id, Model model) {
        System.out.println(id);
        adminMessageRepository.deleteById(id);
        List<AdminMessage>list = (List<AdminMessage>) adminMessageRepository.findAll();
        model.addAttribute("listWithMessagesToAdmin",list);
        return "admin/listWithMessages";
    }
    //grom listWithMessages redirection to adminMainContent
    @RequestMapping("/displayAdminMainContent")
    public String displayAdminMainContent() {
        return "admin/adminMainContent";
    }

}
