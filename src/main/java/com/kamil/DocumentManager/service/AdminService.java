package com.kamil.DocumentManager.service;

import com.kamil.DocumentManager.models.AdminMessage;
import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.AdminMessageRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AdminService {

    private final Logger logger = Logger.getLogger(AdminService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminMessageRepository adminMessageRepository;


    public String adminRadioChoose(String adminChoose) {
        String declaredOption = "";
        switch (adminChoose) {
            case "showAllDocuments":
                declaredOption = "showAllDocuments";
                break;
            case "showAllUsers":
                declaredOption = "showAllUsers";
                break;
            default:
                throw new IllegalArgumentException("no showAllDocuments or showAllUsers");
        }
        logger.log(Level.INFO,"adminRadioChoose");
        return "redirect:" + declaredOption;
    }

    public String sendMessage(String message, Principal principal) {
        String loggedName = "";
        List<User> userList = new ArrayList<>();
        AdminMessage adminMessage = new AdminMessage();
        adminMessage.setMessage(message);
        try {
            loggedName = principal.getName();
            userList = (List<User>) userRepository.findAll();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        try {
            for (User u : userList) {
                if (u.getName().equals(loggedName)) {
                    adminMessage.setUser(u);
                }
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        adminMessageRepository.save(adminMessage);
        logger.log(Level.INFO,"sendMessage");
        return adminMessage.getMessage();
    }
}
