package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import com.kamil.DocumentManager.service.DocumentService;
import com.kamil.DocumentManager.serviceImpl.DocumentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    //starting from /login and sending to login.jsp
    @RequestMapping("/login")
    private String sendToLogin() {
        log.log(Level.INFO, "Login");
        return "login";
    }
    //from login sending to registry.jsp for creating new user
    @RequestMapping("/registry")
    public String registration() {
        log.log(Level.INFO, "Registration");
        return "registry";
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
