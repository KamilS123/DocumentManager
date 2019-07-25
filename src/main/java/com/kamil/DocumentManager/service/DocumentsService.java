package com.kamil.DocumentManager.service;

import com.kamil.DocumentManager.models.Document;
import com.kamil.DocumentManager.models.User;
import com.kamil.DocumentManager.repository.DocumentRepository;
import com.kamil.DocumentManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DocumentsService {
    private final Logger log = Logger.getLogger(DocumentsService.class.getName());
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public void deleteDocumentById(Long docID) {
        //documentRepository.deleteById(docID);  --this is the shortest way
        try {
            documentRepository.delete((Document) getDocumentById(docID));
        } catch (IllegalArgumentException ex) {
            System.out.println("document with passed id not found");
        }
        log.log(Level.INFO, "Delete document");
    }

    public List<Document> sortDocuments(Principal principal) {
        Long id = 0L;
        List<Document> documentList = new ArrayList<>();
        try {
            id = userService.getLoggedUserId(principal);
            //all elements from document tatabase
            documentList = documentRepository.findByLoggedId(id);
            Collections.sort(documentList, Comparator.comparing(Document::getdocument_name));
//        Comparator<Document> documentComparator = (s1,s2)->s1.getdocument_name().compareTo(s2.getdocument_name());
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            System.out.println();
        }
        log.log(Level.INFO, "Delete document");
        return documentList;
    }

    public List<Document> findDocByName(Principal principal, String docNameToFind) {
        Long id = 0L;
        try {
            id = userService.getLoggedUserId(principal);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            System.out.println("there is no user with this id");
        }
        log.log(Level.INFO, "Find doc by name");
        return documentRepository.findDocNameByLoggedId(id, docNameToFind);
    }

    public Document getFile(Long fileId) throws FileNotFoundException {
        return documentRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found " + fileId));
    }

    public List<Document> allDocsForLoggedId(Principal principal) {
        Long id = 0L;
        try {
            id = userService.getLoggedUserId(principal);
        } catch (IllegalArgumentException ex) {
            System.out.println("No logged user with this id");
        }
        log.log(Level.INFO, "allDocsForLoggedId");
        return documentRepository.findByLoggedId(id);
    }

    public String saveEditedDoc(Long docID, String documentComments, String documentName, String documentDescription) {
        try {
            Document document = (Document) getDocumentById(docID);
            document.setdocument_name(documentName);
            document.setDocument_description(documentDescription);
            document.setDocument_comments(documentComments);
            document.setEdition_date();
            documentRepository.save(document);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        log.log(Level.INFO, "Save Edited document");
        return "redirect:docMenuShow";
    }

    public String saveDocToDatabase(Principal principal, String choosenDocument, String documentName, String documentComments, String documentDescription) throws IOException {
        try {
            //prepare string with status of logged user
            String name = principal.getName();
            List<User> userList = (List<User>) userRepository.findAll();
            //getting direction of choosen file
            ClassPathResource uploadedFile = new ClassPathResource(choosenDocument);
            //show file direction to console
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
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }

        log.log(Level.INFO, "saveDocToDatabase");
        return userService.checkUserStatus(principal);
    }

    private Object getDocumentById(Long docID) {
        Optional<Document> documentsList = Optional.of(documentRepository.findAll().stream()
                .filter(s -> s.getId().equals(docID))
                .findAny()
                .orElseThrow(() -> new NotFoundException("passed id not been found")));
        log.log(Level.INFO, "get document by document");
        return documentsList.get();
    }
}
