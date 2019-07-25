package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import com.kamil.DocumentManager.service.DocumentService;
import com.kamil.DocumentManager.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentService documentService;

    //from user main content to creatNewDocumentForm
    @RequestMapping("/createNewDocument")
    public String addDocument() {
        log.log(Level.INFO, "createDocument");
        return "createNewDocumentForm";
    }

    //catching details of new Document and saving them to database and going to userMainContent
    @RequestMapping("/selectDocument")
    public String selectDocument(Principal principal, @RequestParam("choosenDocument") String choosenDocument, @RequestParam("documentName") String documentName, @RequestParam("documentDescription") String documentDescription, @RequestParam("documentComments") String documentComments, Model model) throws IOException {
        //prepare string with status of logged user
        String redirection = userService.checkUserStatus(principal);

        String name = principal.getName();
        List<User> userList = (List<User>) userRepository.findAll();
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
        return redirection;
    }

    //from userMainContent(menu) for editing choosen file. Showing list with documents
    @RequestMapping("/docMenuShow")
    public String documentEdition(Principal principal, Model model) {
        String redirection = userService.checkUserStatus(principal);
        Long id = userService.getLoggedUserId(principal);
        //all elements from document tatabase
        List<Document> documentList = documentRepository.findByLoggedId(id);
        model.addAttribute("docNameToFind", documentList);
        log.log(Level.INFO, "Document show");
        return redirection;
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
    public String editDocFromList(@RequestParam("docID") Long docID, Principal principal,Model model) {
        String redirection = userService.checkUserStatus(principal);
        List<Document> documentsList = (List<Document>) documentRepository.findAll();
        //passed name is contained in documentsList, than delete
        for (Document document : documentsList) {
            if (document.getId() == docID) {
                documentRepository.delete(document);
            }
        }
        log.log(Level.INFO, "Delete document");
        model.addAttribute("docNameToFind",documentsList);
//        return redirection;
        return "redirect:docMenuShow";
    }

    //from editDocumentContent passing parameters to edit document
    @RequestMapping(value = "/saveEditedDocuments", method = RequestMethod.POST)
    public String saveEditedDocuments(Principal principal, @RequestParam("docID") Long docID, @RequestParam("documentNameValue") String documentName, @RequestParam("documentDescription") String documentDescription, @RequestParam("documentComments") String documentComments, Model model) {
        //get local time and update edited time
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Document> documentsList = (List<Document>) documentRepository.findAll();
        for (Document document : documentsList) {
            if (document.getId() == docID) {
                documentRepository.updateDocument(documentName, documentComments, documentDescription, docID, localDateTime);
//                model.addAttribute("docNameToFind",documentsList);
                log.log(Level.INFO, "Save edited document");
//                return redirection;
                return "redirect:docMenuShow";
            }
        }
        System.out.println("passed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + docID);
        return "saved";
    }

    @RequestMapping("/findDocByName")
    public String findDocByName(Principal principal, @RequestParam("docNameToFind") String docNameToFind, Model model) {
        String redirection = userService.checkUserStatus(principal);
        Long id = userService.getLoggedUserId(principal);
        //all elements from document tatabase
        List<Document> documentList = documentRepository.findDocNameByLoggedId(id, docNameToFind);
        model.addAttribute("docNameToFind", documentList);
        log.log(Level.INFO, "Find doc by name");
        return redirection;
    }

    @RequestMapping("/sortByName")
    public String sortByName(Principal principal, Model model) {
        String redirection = userService.checkUserStatus(principal);
        Long id = userService.getLoggedUserId(principal);
        //all elements from document tatabase
        List<Document> documentList = documentRepository.findByLoggedId(id);
        Collections.sort(documentList, Comparator.comparing(Document::getdocument_name));
//        Comparator<Document> documentComparator = (s1,s2)->s1.getdocument_name().compareTo(s2.getdocument_name());
        model.addAttribute("docNameToFind", documentList);
        return redirection;
    }
    //view document
    @RequestMapping("/viewDocument")
    public String viewDocument(@RequestParam("docID") Long id, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
        List<Document> documentList = (List<Document>) documentRepository.findAll();
        String encodedObject = "";
        for (Document document : documentList) {
            if (document.getId() == id) {
                byte[] encoded = Base64.getEncoder().encode(document.getContent());
                encodedObject = new String(encoded, "utf-8");
            }
            model.addAttribute("userImage", encodedObject);
        }
        return "documentView";
    }

    //from userMainContent downloading document
    @RequestMapping("/downloadDocument")
    @ResponseBody
    public DefaultResourceLoader getFile(@RequestParam("docID") Long docID, HttpServletResponse response){
        DefaultResourceLoader loader = new DefaultResourceLoader();
        try {
            InputStream is = new ByteArrayInputStream(documentService.getFile(docID).getContent());
            IOUtils.copy(is, response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=Accepted.pdf");
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }
        return loader;
    }
    /*@RequestMapping("/downloadDocument")
    public ResponseEntity<Resource>downloadFile(@RequestParam("docID")Long docID) throws FileNotFoundException {
        Document document = documentService.getFile(docID);
        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(document.getdocument_name()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment filename=\""+document.getdocument_name()+"\"")
                .body(new ByteArrayResource(document.getContent()));
    }*/
}
