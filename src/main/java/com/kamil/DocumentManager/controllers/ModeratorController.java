package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import com.kamil.DocumentManager.service.DocumentsService;
import com.kamil.DocumentManager.service.ModeratorService;
import com.kamil.DocumentManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ModeratorController {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModeratorService moderatorService;

    @RequestMapping("/findDocByNameModerator")
    public String findDocByNameModerator() {
        return "moderatorMainContent";
    }

    //grom userMainContent after changed status. Main controller for moderator. Sending to diffirent controllers.
    @RequestMapping("/moderatorMainController")
    public String moderatorMainController(@RequestParam("moderatorMenuRadio")String moderatorChoose) {
        String choose = moderatorService.moderatorRadioChoose(moderatorChoose);
        return choose;
    }
    //from moderatorMainController for display just user with user status because moderator can`t see admin details
    @RequestMapping("/showUsers")
    public String ShowAllUsers(Model model) {
        List<User> justUserStatusList = userService.getUsersWithStatusUser();
        model.addAttribute("userList",justUserStatusList);
        return "allUsersTable";
    }

    //from moderatorMainController for display list with documentss for all users
    @RequestMapping("/showAllDocs")
    public String docMenuShowModerator(Model model) {
        List<Document> documentList = documentRepository.findAll();
        model.addAttribute("docNameToFind",documentList);
        return "moderator/moderatorMainContent";
    }
    //from showAllDocs by moderatorMainController. Changing active or banned user
    @RequestMapping("/lockUnlock")
    public String lockUnlock(@RequestParam("userID")Long userID, Principal principal) {
        String redirect = userService.checkUserStatus(principal);
        String redirectTrack = "";
        String status = "";
        List<User>userList = (List<User>) userRepository.findAll();
        for(User u : userList) {
            if (u.getId() == userID) {
                if (u.getActivationStatus().equals("active")) {
                    status = "banned";
                } else if (u.getActivationStatus().equals("banned")) {
                    status = "active";
                }
            }
        }
        userRepository.updateActivationStatus(status,userID);
        if (redirect.equals("moderator")) {
            redirectTrack = "redirect:showUsers";
        }else {
            redirectTrack = "redirect:showAllUsers";
        }
        return redirectTrack;
    }
}
