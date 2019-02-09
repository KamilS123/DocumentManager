package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class DocumentController {
    private static final Logger log = Logger.getLogger(DocumentController.class.getName());
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    //from user main content to creatNewDocumentForm
    @RequestMapping("/createNewDocument")
    public String addDocument() {
        log.log(Level.INFO, "createDocument");
        return "createNewDocumentForm";

    }

    //catching details of new Document and saving them to database and going to userMainContent
    @RequestMapping("/selectDocument")
    public String selectDocument(@RequestParam("choosenDocument")String choosenDocument, @RequestParam("documentName")String documentName, @RequestParam("documentDescription") String documentDescription, @RequestParam("documentComments") String documentComments, Model model) throws IOException {
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
        log.log(Level.INFO, "Select document");
        return "userMainContent";
    }

    //from userMainContent(menu) for editing choosen file. Sowing list with documents
    @RequestMapping("/docMenuShow")
    public String documentEdition(Model model) {
        //all elements from document tatabase
        List<Document> documentList = (List<Document>) documentRepository.findAll();
        model.addAttribute("docNameToFind", documentList);
        log.log(Level.INFO, "Document show");
//        return "documentList";
        return "userMainContent";
    }

    //from documentMenu to edit. User decided edit
    @RequestMapping("/editDocFromList")
    public String editForm(@RequestParam("docID") Long docID, Model model) {
        model.addAttribute("docID", docID);
        log.log(Level.INFO, "Edit doc from list");
        return "editDocumentContent";
    }

    //from documentMenu. User decided delete document
    @RequestMapping("/deleteDocument")
    public String editDocFromList(@RequestParam("docID") Long docID, Model model) {
        List<Document> documentsList = (List<Document>) documentRepository.findAll();
        //passed name is contained in documentsList, than delete
        for (Document document : documentsList) {
            if (document.getId() == docID) {
                documentRepository.delete(document);
            }
        }
        log.log(Level.INFO, "Delete document");
        return "userMainContent";
    }

    //from editDocumentContent passing parameters to edit document
    @RequestMapping(value = "/saveEditedDocuments", method = RequestMethod.POST)
    public String saveEditedDocuments(@RequestParam("docID") Long docID, @RequestParam("documentNameValue") String documentName, @RequestParam("documentDescription") String documentDescription, @RequestParam("documentComments") String documentComments, Model model) {
        //getting local time for updatind etition time
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Document> documentsList = (List<Document>) documentRepository.findAll();
        for (Document document : documentsList) {
            if (document.getId() == docID) {
                documentRepository.updateDocument(documentName, documentComments, documentDescription, docID, localDateTime);
                log.log(Level.INFO, "Save edited document");
                return "userMainContent";
            }
        }
        System.out.println("passed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + docID);
        return "saved";
    }

    @RequestMapping("/findDocByName")
    public String findDocByName(@RequestParam("docNameToFind") String docNameToFind, Model model) {
        List<Document>newListByName = documentRepository.findDocByName(docNameToFind);
        model.addAttribute("docNameToFind", newListByName);
        log.log(Level.INFO, "Find doc by name");
        return "userMainContent";
    }

    @RequestMapping("/sortByName")
    public String sortByName(Model model) {
        List<Document>documents = documentRepository.findAll();

        Collections.sort(documents, Comparator.comparing(Document::getdocument_name));
//        Comparator<Document> documentComparator = (s1,s2)->s1.getdocument_name().compareTo(s2.getdocument_name());
        model.addAttribute("docNameToFind",documents);
        return "userMainContent";
    }
}
