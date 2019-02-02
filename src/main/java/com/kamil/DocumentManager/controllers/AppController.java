package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class AppController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    //starting from /login and sending to login.jsp
    @RequestMapping("/login")
    private String sendToLogin() {
        return "login";
    }

    //httpServletResquest zawiera informacje o serverach
    @RequestMapping("/server")
    @ResponseBody
    public String set(HttpServletRequest request) {
        String browserName = request.getHeader("User-Agent");
        String id = request.getRemoteAddr();
        return browserName + " ...... " + id;
    }
}
