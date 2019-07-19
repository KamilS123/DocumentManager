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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class ModeratorController {
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/findDocByNameModerator")
    public String findDocByNameModerator() {
        return "moderatorMainContent";
    }

    //grom userMainContent after changed status. Main controller for moderator. Sending to diffirent controllers.
    @RequestMapping("/moderatorMainController")
    public String moderatorMainController(@RequestParam("moderatorMenuRadio")String moderatorChoose, Model model) {
        String option = "";
        switch(moderatorChoose) {
            case "ShowAllDocs":
                option = "ShowAllDocs";
                break;
            case "ShowAllUsers":
                option = "ShowAllUsers";
                break;
        }
        return "redirect:" + option;
    }
    //from moderatorMainController for display just user with user status because moderator can`t see admin details
    @RequestMapping("/ShowAllUsers")
    public String ShowAllUsers(Model model) {
       List<User>allUserList = (List<User>) userRepository.findAll();
       List<User>justUserStatusList = allUserList.stream()
                .filter(s->s.getStatus().equals("user"))
                .collect(Collectors.toList());
        model.addAttribute("userList",justUserStatusList);
        return "moderator/allUsersTable";
    }
    //from moderatorMainController for display list with documentss for all users
    @RequestMapping("/ShowAllDocs")
    public String docMenuShowModerator(Model model) {
        List<Document> documentList = documentRepository.findAll();
        model.addAttribute("docNameToFind",documentList);
        return "moderator/moderatorMainContent";
    }
    //from showAllDocs by moderatorMainController. Changing active or banned user
    @RequestMapping("/lockUnlock")
    public String lockUnlock(@RequestParam("userID")Long userID) {
        List<User>userList = (List<User>) userRepository.findAll();
        String status = "";
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
        return "moderator/moderatorMainContent";
    }
}
