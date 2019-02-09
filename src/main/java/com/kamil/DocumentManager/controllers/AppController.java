package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;


@Controller
@RequestMapping()
public class AppController {
    private static final Logger log = Logger.getLogger(AppController.class.getName());

   /* @Autowired
    DocumentService documentService;*/

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @RequestMapping("/")
    public String goToLogin() {
        return "login";
    }

    //starting from /login and sending to login.jsp
    @RequestMapping("/login")
    public String sendToLogin() {
        log.log(Level.INFO, "Login");
        return "login";
    }
    //from login sending to registry.jsp for creating new user
    @RequestMapping("/registry")
    public String registration() {
        log.log(Level.INFO, "Registration");
        return "registry";
    }

    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request) {
        Cookie[]cookies = request.getCookies();
        for(Cookie cook : cookies) {
            if(cook.getName().equals("loginID")) {
                cook.setMaxAge(0);
            }
        }
        return "login";
    }

    //from documentList to userMainContent
    @RequestMapping("/goToMainContent")
    public String goToMainContent() {
        log.log(Level.INFO, "Go to main content");
        return "userMainContent";
    }

    //httpServletResquest zawiera informacje
    @RequestMapping("/server")
    @ResponseBody
    public String set(HttpServletRequest request) {
        String browserName = request.getHeader("User-Agent");
        String id = request.getRemoteAddr();
        log.log(Level.INFO, "Browser info,, Id info");
        return browserName + " ...... " + id;
    }

    //stronicowanie
    /*private Page<Document>getAllDocumentsPagable(int page) {
        int elements = 5;
        Page<Document>pages = documentRepository.findAll(PageRequest.of(page,elements));
        for (Document document : pages) {
            int nrRoli = document.getContent().length;
        }
    }*/
}
