package com.kamil.DocumentManager.service;

import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserService {

    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    //get list of users with status "user"
    public List<User> getUsersWithStatusUser() {
        List<User> allUserList = new ArrayList<>();
        try {
            allUserList = (List<User>) userRepository.findAll();
        } catch (IllegalArgumentException ex) {
            System.out.println("could not find users with status user");
        }
        logger.log(Level.INFO, "getUsersWithStatusUser");
        return allUserList.stream()
                .filter(s -> s.getStatus().equals("user"))
                .collect(Collectors.toList());
    }

    //check password and if repeat password is correct than update new
    public String changePassword(Principal principal, String password, String repeatPassword) {
        String redirection = "";
        try {
            redirection = checkUserStatus(principal);
            if (password.equals(repeatPassword)) {
                String name = principal.getName();
                List<User> userList = (List<User>) userRepository.findAll();
                for (User user : userList) {
                    if (user.getName().equals(name)) {
                        userRepository.updatePassword(passwordEncoder.encode(password));
                        logger.log(Level.INFO, "password changed ");
                        return redirection;
                    }
                }
            } else {
                logger.log(Level.INFO, "redirect to changePasswordForm ");
                redirection = "redirect:changePasswordForm";
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        return redirection;
    }

    public void saveNewUser(String registryName, String registrySurname, String registryPassword) throws IllegalArgumentException {
        List<User>userList = (List<User>) userRepository.findAll();
        userList.stream()
                .filter(s -> s.getName().equals(registryName))
                .findFirst()
                .ifPresent(s-> {
                    throw new IllegalArgumentException("Username is already in database");
                });
        try {
            User user = new User();
            user.setName(registryName);
            user.setSurname(registrySurname);
            user.setPassword(passwordEncoder.encode(registryPassword));
            user.setStatus("user");
            user.setActivationStatus("active");
            //saving user to database
            userRepository.save(user);
        } catch (Exception ex) {
            System.out.println("user could not be saved");
        }
        logger.log(Level.INFO, "saveNewUser");
    }

    //check status of logged user by fetch principal
    public String checkUserStatus(Principal principal) {
        String status = "";
        try {
            String name = principal.getName();
            List<User> userList = (List<User>) userRepository.findAll();
            for (User user : userList) {
                if (user.getName().equals(name)) {
                    status = user.getStatus();
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        //prepare return statement to controllers, depend of logged user status
        String redirection = "";
        switch (status) {
            case "admin":
                redirection = "admin/adminMainContent";
                break;
            case "moderator":
                redirection = "moderator/moderatorMainContent";
                break;
            case "user":
                redirection = "userMainContent";
                break;
            default:
                throw new IllegalArgumentException("unknown status");
        }
        logger.log(Level.INFO, "checkUserStatus");
        return redirection;
    }

    public String changeStatusValue(Principal principal, String changeStatusValue) {
        Long id = 0L;
        try {
            id = userService.getLoggedUserId(principal);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        String status = "";
        String redirecion = "";
        switch (changeStatusValue) {
            case "user":
                status = "user";
                redirecion = "userMainContent";
                break;
            case "moderator":
                status = "moderator";
                redirecion = "moderator/moderatorMainContent";
                break;
            case "admin":
                status = "admin";
                redirecion = "admin/adminMainContent";
                break;
            default:
                throw new IllegalArgumentException("unknown status");
        }
        userRepository.updateStatus(status, id);
        logger.log(Level.INFO, "changeStatusValue");
        return redirecion;
    }

    //fetch logged user to get and return his id
    public Long getLoggedUserId(Principal principal) {
        Long id = 1L;
        try {
            String name = principal.getName();
            List<User> userList = (List<User>) userRepository.findAll();
            for (User user : userList) {
                if (user.getName().equals(name)) {
                    id = user.getId();
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        logger.log(Level.INFO, "getLoggedUserId");
        return id;
    }
}
