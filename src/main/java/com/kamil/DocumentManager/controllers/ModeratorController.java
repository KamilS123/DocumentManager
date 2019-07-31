package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.service.ModeratorService;
import com.kamil.DocumentManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class ModeratorController {

    private final Logger logger = Logger.getLogger(ModeratorController.class.getName());

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModeratorService moderatorService;

    @RequestMapping("/findDocByNameModerator")
    public String findDocByNameModerator() {
        logger.log(Level.INFO,"findDocByNameModerator");
        return "moderatorMainContent";
    }

    //from userMainContent after changed status. Main controller for moderator. Sending to diffirent controllers.
    @RequestMapping("/moderatorMainController")
    public String moderatorMainController(@RequestParam("moderatorMenuRadio")String moderatorChoose) {
        String choose = moderatorService.moderatorRadioChoose(moderatorChoose);
        logger.log(Level.INFO,"moderatorMainController");
        return choose;
    }
    //from moderatorMainController for display just user with user status because moderator can`t see admin details
    @RequestMapping("/showUsers")
    public String ShowAllUsers(Model model) {
        model.addAttribute("userList",userService.getUsersWithStatusUser());
        logger.log(Level.INFO,"showUsers");
        return "allUsersTable";
    }

    //from moderatorMainController for display list with documentss for all users
    @RequestMapping("/showAllDocs")
    public String docMenuShowModerator(Model model) {
        model.addAttribute("docNameToFind",documentRepository.findAll());
        logger.log(Level.INFO,"showAllDocs");
        return "moderator/moderatorMainContent";
    }
    //from showAllDocs by moderatorMainController. Changing active or banned user
    @RequestMapping("/lockUnlock")
    public String lockUnlock(@RequestParam("userID")Long userID, Principal principal) {
        logger.log(Level.INFO,"lockUnlock");
        return moderatorService.lockOrUnockUser(principal,userID);
    }
}
