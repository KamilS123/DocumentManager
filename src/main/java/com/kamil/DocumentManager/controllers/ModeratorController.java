package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ModeratorController {
    @Autowired
    DocumentRepository documentRepository;

    @RequestMapping("/findDocByNameModerator")
    public String findDocByNameModerator() {
        return "moderatorMainContent";
    }

    @RequestMapping("/docMenuShowModerator")
    public String docMenuShowModerator(Model model) {
        List<Document> documentList = documentRepository.findAll();
        model.addAttribute("docNameToFind",documentList);
        return "moderator/moderatorMainContent";
    }
}
