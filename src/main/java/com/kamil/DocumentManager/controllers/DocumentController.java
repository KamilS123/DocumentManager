package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/")
public class DocumentController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    //from user main content to creatNewDocumentForm
    @RequestMapping("/createNewDocument")
    public String addDocument(@RequestParam("loginID") String loginID, @RequestParam("loginSurname") String loginSurname, @RequestParam("loginName") String loginName, Model model) {
        model.addAttribute("loginSurname", loginSurname);
        model.addAttribute("loginName", loginName);
        model.addAttribute("loginID", loginID);
        return "createNewDocumentForm";
    }

    //catching details of new Document and saving them to database and going to userMainContent
    @RequestMapping("/selectDocument")
    public String selectDocument(@RequestParam("loginID") String loginID, @RequestParam("loginSurname") String loginSurname, @RequestParam("loginName") String loginName, @RequestParam("choosenDocument") String choosenDocument, @RequestParam("documentName") String documentName, @RequestParam("documentDescription") String documentDescription, @RequestParam("documentComments") String documentComments, Model model) throws IOException {
        //getting direction of choosen file
        ClassPathResource uploadedFile = new ClassPathResource(choosenDocument);
        //showing file direction in console
        System.out.println(choosenDocument);
        //our object as byte[]
        byte[] arrayPic = new byte[(int) uploadedFile.contentLength()];
        uploadedFile.getInputStream().read(arrayPic);
        //creating new object Document for adding to database
        Document uploadedDocument = new Document();
        uploadedDocument.setdocument_name(documentName);
        uploadedDocument.setDocument_comments(documentComments);
        uploadedDocument.setDocument_description(documentDescription);
        uploadedDocument.setContent(arrayPic);
        uploadedDocument.setAdd_date();
        uploadedDocument.setEdition_date();
        //saving object to database by repository
        documentRepository.save(uploadedDocument);

        model.addAttribute("loginSurname", loginSurname);
        model.addAttribute("loginName", loginName);
        model.addAttribute("loginID", loginID);
        return "userMainContent";
    }

    //from userMainContent(menu) for editing choosen file. Sowing list with documents
    @RequestMapping("/docMenuShow")
    public String documentEdition(@RequestParam("loginID") String loginID, @RequestParam("loginSurname") String loginSurname, @RequestParam("loginName") String loginName, Model model) {
        //all elements from document tatabase
        List<Document> documentList = (List<Document>) documentRepository.findAll();
        model.addAttribute("documentList", documentList);
        model.addAttribute("loginID", loginID);
        model.addAttribute("loginSurname", loginSurname);
        model.addAttribute("loginName", loginName);
        return "documentList";
    }

    //from documentMenu to edit. User decided edit
    @RequestMapping("/editDocFromList")
    public String editForm(@RequestParam("loginSurname") String loginSurname, @RequestParam("loginName") String loginName, @RequestParam("loginID") Long loginID, @RequestParam("docID") Long docID, Model model) {
        model.addAttribute("docID", docID);
        model.addAttribute("loginID", loginID);
        model.addAttribute("loginSurname", loginSurname);
        model.addAttribute("loginName", loginName);
        return "editDocumentContent";
    }

    //from documentMenu. User decided delete document
    @RequestMapping("/deleteDocument")
    public String editDocFromList(@RequestParam("loginSurname") String loginSurname, @RequestParam("loginName") String loginName, @RequestParam("loginID") Long loginID, @RequestParam("docID") Long docID, Model model) {
        List<Document> documentsList = (List<Document>) documentRepository.findAll();
        //passed name is contained in documentsList, than delete
        for (Document document : documentsList) {
            if (document.getId() == docID) {
                documentRepository.delete(document);
            }
        }
        model.addAttribute("loginID", loginID);
        model.addAttribute("loginSurname", loginSurname);
        model.addAttribute("loginName", loginName);
        return "userMainContent";
    }

    //from editDocumentContent passing parameters to edit document
    @RequestMapping(value = "/saveEditedDocuments", method = RequestMethod.POST)
    public String saveEditedDocuments(@RequestParam("loginSurname") String loginSurname, @RequestParam("loginName") String loginName, @RequestParam("loginID") Long loginID, @RequestParam("docID") Long docID, @RequestParam("documentNameValue") String documentName, @RequestParam("documentDescription") String documentDescription, @RequestParam("documentComments") String documentComments, Model model) {
        //getting local time for updatind etition time
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Document> documentsList = (List<Document>) documentRepository.findAll();
        for (Document document : documentsList) {
            if (document.getId() == docID) {
                /*System.out.println("id tooooooooooo    !!!!!!!!"  + docID);*/
                model.addAttribute("loginID", loginID);
                model.addAttribute("loginSurname", loginSurname);
                model.addAttribute("loginName", loginName);
                //update document details
                documentRepository.updateDocument(documentName, documentComments, documentDescription, docID, localDateTime);
                return "userMainContent";
            }
        }
        System.out.println("passed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + docID);
        return "saved";
    }
    //
    @RequestMapping("/findDocByName")
    public String findDocByName(@RequestParam("docNameToFind")String docNameToFind, Model model) {
        List<Document>listByName =(List<Document>) documentRepository.findDocByName(docNameToFind);
        model.addAttribute("docNameToFind", listByName);
        return "userMainContent";
    }
}
