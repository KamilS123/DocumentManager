package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.repository.AdminMessageRepository;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import com.kamil.DocumentManager.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class AdminController {

    private final Logger logger = Logger.getLogger(AdminController.class.getName());

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private AdminMessageRepository adminMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminService adminService;

    @RequestMapping("/adminMainController")
    public String adminMainController(@RequestParam("adminMenuRadio") String adminChoose) {
        logger.log(Level.INFO, "adminMainController");
        return adminService.adminRadioChoose(adminChoose);
    }

    @RequestMapping("/showAllDocuments")
    public String showAllDocuments(Model model) {
        model.addAttribute("docNameToFind", documentRepository.findAll());
        logger.log(Level.INFO, "showAllDocuments");
        return "admin/adminMainContent";
    }

    @RequestMapping("/showAllUsers")
    public String showAllUsers(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        logger.log(Level.INFO, "showAllUsers");
        return "allUsersTable";
    }

    @RequestMapping("/displayMessagesFromUsers")
    public String displayMessagesFromUsers(Model model) {
        model.addAttribute("listWithMessagesToAdmin", adminMessageRepository.findAll());
        logger.log(Level.INFO, "displayMessagesFromUsers");
        return "admin/listWithMessages";
    }

    //from listWithMessage by input. Delete adminMessage from databese by passed id and resend list with messages
    @RequestMapping("/deleteMessageFromUsers")
    public String deleteMessageFromUsers(@RequestParam("deleteMessageID") Long id, Model model) {
        adminMessageRepository.deleteById(id);
        model.addAttribute("listWithMessagesToAdmin", adminMessageRepository.findAll());
        logger.log(Level.INFO, "deleteMessageFromUsers");
        return "admin/listWithMessages";
    }

    //from listWithMessages redirection to adminMainContent
    @RequestMapping("/displayAdminMainContent")
    public String displayAdminMainContent() {
        logger.log(Level.INFO, "displayAdminMainContent");
        return "admin/adminMainContent";
    }

}
