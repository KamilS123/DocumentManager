package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.Session;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
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
    public String selectDocument(Principal principal, @RequestParam("choosenDocument")String choosenDocument, @RequestParam("documentName")String documentName, @RequestParam("documentDescription") String documentDescription, @RequestParam("documentComments") String documentComments, Model model) throws IOException {
        String name = principal.getName();
        List<User>userList = (List<User>) userRepository.findAll();


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

        for (User user : userList) {
            if (user.getName().equals(name)) {
                uploadedDocument.setUser(user);
            }
        }

        //saving object to database by repository
        documentRepository.save(uploadedDocument);
        log.log(Level.INFO, "Select document");
        return "userMainContent";
    }

    //from userMainContent(menu) for editing choosen file. Sowing list with documents
    @RequestMapping("/docMenuShow")
    public String documentEdition(Principal principal, Model model) {
        Long id = 0L;
        String name = principal.getName();
        List<User>userList = (List<User>) userRepository.findAll();
        for (User user : userList) {
            if (user.getName().equals(name)) {
                id = user.getId();
            }
        }
        //all elements from document tatabase
        List<Document>documentList = documentRepository.findByLoggedId(id);
        model.addAttribute("docNameToFind", documentList);
        log.log(Level.INFO, "Document show");
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
    public String findDocByName(Principal principal, @RequestParam("docNameToFind") String docNameToFind, Model model) {
        Long id = 0L;
        String name = principal.getName();
        List<User>userList = (List<User>) userRepository.findAll();
        for (User user : userList) {
            if (user.getName().equals(name)) {
                id = user.getId();
            }
        }
        //all elements from document tatabase
        List<Document>documentList = documentRepository.findDocNameByLoggedId(id,docNameToFind);
//        List<Document>newListByName = documentRepository.findDocByName(docNameToFind);
        model.addAttribute("docNameToFind", documentList);
        log.log(Level.INFO, "Find doc by name");
        return "userMainContent";
    }

    @RequestMapping("/sortByName")
    public String sortByName(Principal principal,Model model) {
        Long id = 0L;
        String name = principal.getName();
        List<User>userList = (List<User>) userRepository.findAll();
        for (User user : userList) {
            if (user.getName().equals(name)) {
                id = user.getId();
            }
        }
        //all elements from document tatabase
        List<Document>documentList = documentRepository.findByLoggedId(id);
        Collections.sort(documentList, Comparator.comparing(Document::getdocument_name));
//        Comparator<Document> documentComparator = (s1,s2)->s1.getdocument_name().compareTo(s2.getdocument_name());
        model.addAttribute("docNameToFind",documentList);
        return "userMainContent";
    }

    //view document
    @RequestMapping("/viewDocument")
    public String viewDocument(@RequestParam("docID") Long id,Model model, HttpServletResponse response) throws UnsupportedEncodingException {
        List<Document>documentList = (List<Document>) documentRepository.findAll();
        String encodedObject = "";
        for(Document document : documentList) {
            if (document.getId()==id) {
                byte[] encoded = Base64.getEncoder().encode(document.getContent());
                encodedObject = new String(encoded,"utf-8");
            }
            model.addAttribute("userImage",encodedObject);
        }
        return "documentView";
    }

    //from userMainContent downloading document
    /*@RequestMapping("/downloadDocument")
    public void getFile(@RequestParam("docID") Long docID, HttpServletResponse response){
        try {
            DefaultResourceLoader loader = new DefaultResourceLoader();
            InputStream is = new ByteArrayInputStream(documentRepository.);
            IOUtils.copy(is, response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=Accepted.pdf");
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
    }*/
}
