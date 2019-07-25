package com.kamil.DocumentManager.controllers;

import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.service.DocumentsService;
import com.kamil.DocumentManager.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class DocumentController {

    private static final Logger log = Logger.getLogger(DocumentController.class.getName());

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentsService documentsService;

    //from user main content to creatNewDocumentForm
    @RequestMapping("/createNewDocument")
    public String addDocument() {
        log.log(Level.INFO, "createDocument");
        return "createNewDocumentForm";
    }

    //catching details of new Document and saving them to database and going to userMainContent
    @RequestMapping("/selectDocument")
    public String saveDocumentToDatabase(Principal principal, @RequestParam("choosenDocument") String choosenDocument, @RequestParam("documentName") String documentName, @RequestParam("documentDescription") String documentDescription, @RequestParam("documentComments") String documentComments) throws IOException {
        log.log(Level.INFO, "Select document");
        return documentsService.saveDocToDatabase(principal, choosenDocument, documentName, documentComments, documentDescription);
    }

    //from userMainContent(menu) for editing choosen file. Showing list with documents
    @RequestMapping("/docMenuShow")
    public String returnToCorrectMenuWithDocumentList(Principal principal, Model model) {
        model.addAttribute("docNameToFind", documentsService.allDocsForLoggedId(principal));
        log.log(Level.INFO, "Document show");
        return userService.checkUserStatus(principal);
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
    public String deleteDocument(@RequestParam("docID") Long docID) {
        documentsService.deleteDocumentById(docID);
        log.log(Level.INFO, "Edit doc from list");
        return "redirect:docMenuShow";
    }

    //from editDocumentContent passing parameters to edit document
    @RequestMapping(value = "/saveEditedDocuments", method = RequestMethod.POST)
    public String saveEditedDocuments(@RequestParam("docID") Long docID, @RequestParam("documentNameValue") String documentName, @RequestParam("documentDescription") String documentDescription, @RequestParam("documentComments") String documentComments) {
        log.log(Level.INFO, "Save Edited Documents");
        return documentsService.saveEditedDoc(docID, documentComments, documentName, documentDescription);
    }

    @RequestMapping("/findDocByName")
    public String findDocByName(Principal principal, @RequestParam("docNameToFind") String docNameToFind, Model model) {
        model.addAttribute("docNameToFind", documentsService.findDocByName(principal, docNameToFind));
        log.log(Level.INFO, "Find doc by name");
        return userService.checkUserStatus(principal);
    }

    @RequestMapping("/sortByName")
    public String sortByName(Principal principal, Model model) {
        model.addAttribute("docNameToFind", documentsService.sortDocuments(principal));
        log.log(Level.INFO, "Find doc by name");
        return userService.checkUserStatus(principal);
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
    public DefaultResourceLoader getFile(@RequestParam("docID") Long docID, HttpServletResponse response) {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        try {
            InputStream is = new ByteArrayInputStream(documentsService.getFile(docID).getContent());
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
