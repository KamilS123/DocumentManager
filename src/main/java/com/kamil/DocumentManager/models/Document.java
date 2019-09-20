package com.kamil.DocumentManager.models;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue
    @Column(name = "document_id")
    private Long id;

    @Column(name = "documentName")
    @NotBlank(message = "documentName can not be blank")
    private String documentName;

    @Column(name = "documentDescription")
    private String documentDescription;

    @Column(name = "documentComments")
    private String documentComments;

    @Column(name = "additionDate")
    private LocalDateTime additionDate;

    @Column(name = "editionDate")
    private LocalDateTime editionDate;

    @Column(name = "content")
    @NotNull(message = "documentName can not be null")
    @Lob
    private byte[] content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Document() {
    }

    public Document(String documentName, String documentDescription, String documentComments, LocalDateTime additionDate, LocalDateTime editionDate, byte[] content) {
        this.documentName = documentName;
        this.documentDescription = documentDescription;
        this.documentComments = documentComments;
        this.additionDate = additionDate;
        this.editionDate = editionDate;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public String getDocumentComment() {
        return documentComments;
    }

    public void setDocumentComment(String documentComments) {
        this.documentComments = documentComments;
    }

    public LocalDateTime getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate() {
        this.additionDate = LocalDateTime.now();
    }

    public LocalDateTime getEditionDate() {
        return editionDate;
    }

    public void setEditionDate() {
        this.editionDate = LocalDateTime.now();
    }

    public byte[] getContent() {
        return content;
    }

    public String getDocContent() throws UnsupportedEncodingException {
        byte[] encodeBase64 = Base64.encodeBase64(this.content);
        String base64Encoded = new String(encodeBase64, "UTF-8");
        return base64Encoded;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", documentName='" + documentName + '\'' +
                ", documentDescription='" + documentDescription + '\'' +
                ", documentComments='" + documentComments + '\'' +
                ", additionDate=" + additionDate +
                ", editionDate=" + editionDate +
                ", content=" + content +
                '}';
    }
}
