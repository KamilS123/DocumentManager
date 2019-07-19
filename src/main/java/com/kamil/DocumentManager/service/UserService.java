package com.kamil.DocumentManager.service;

import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //check status of logged user by fetch principal
    public String checkUserStatus(Principal principal) {
        String status = "";
        String name = principal.getName();
        List<User> userList = (List<User>) userRepository.findAll();
        for (User user : userList) {
            if (user.getName().equals(name)) {
                status = user.getStatus();
            }
        }
        //prepare return statement to controllers, depend of logged user status
        String redirection = "";
        switch (status) {
            case "admin": redirection = "admin/adminMainContent";
                break;
            case "moderator": redirection = "moderator/moderatorMainContent";
                break;
            case "user": redirection = "userMainContent";
                break;
        }
        return redirection;
    }

    //fetch logged user to get and return his id
    public Long getLoggedUserId(Principal principal) {
        Long id = 0L;
        String name = principal.getName();
        List<User> userList = (List<User>) userRepository.findAll();
        for (User user : userList) {
            if (user.getName().equals(name)) {
                id = user.getId();
            }
        }
        return id;
    }

    //get list of users with status "user"
    public List<User> getUsersWithStatusUser() {
        List<User>allUserList = (List<User>) userRepository.findAll();
        return allUserList.stream()
                .filter(s->s.getStatus().equals("user"))
                .collect(Collectors.toList());
    }
}
