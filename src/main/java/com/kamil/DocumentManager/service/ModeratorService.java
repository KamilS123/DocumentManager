package com.kamil.DocumentManager.service;

import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ModeratorService {

    Logger logger = Logger.getLogger(ModeratorService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public String lockOrUnockUser(Principal principal, Long userID) {
        String redirectTrack = "";
        String status = "";
        String redirect = "";
        List<User> userList = new ArrayList<>();
        try {
            redirect = userService.checkUserStatus(principal);
            userList = (List<User>) userRepository.findAll();
        } catch (NullPointerException ex) {
            System.out.println("user has no status");
        }
        try {
            for (User u : userList) {
                if (u.getId() == userID) {
                    if (u.getActivationStatus().equals("active")) {
                        status = "banned";
                    } else if (u.getActivationStatus().equals("banned")) {
                        status = "active";
                    }
                }
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("user status is not banned or active");
        }
        userRepository.updateActivationStatus(status, userID);
        if (redirect.equals("moderator")) {
            redirectTrack = "redirect:showUsers";
        } else {
            redirectTrack = "redirect:showAllUsers";
        }
        logger.log(Level.INFO, "lockOrUnockUser");
        return redirectTrack;
    }

    public String moderatorRadioChoose(String moderatorChoose) {
        String option = "";
        switch (moderatorChoose) {
            case "showAllDocs":
                option = "showAllDocs";
                break;
            case "showUsers":
                option = "showUsers";
                break;
            default:
                throw new IllegalArgumentException("user redirected to other source");
        }
        logger.log(Level.INFO, "moderatorRadioChoose");
        return "redirect:" + option;
    }
}
