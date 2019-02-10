package com.kamil.DocumentManager.models;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue
    @Column(name = "document_id")
    private Long id;

    @Column(name = "document_name")
    private String document_name;

    @Column(name = "document_description")
    private String document_description;

    @Column(name = "document_comments")
    private String document_comments;

    @Column(name = "add_date")
    private LocalDateTime add_date;

    @Column(name = "edition_date")
    private LocalDateTime edition_date;

    @Column(name = "content")
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

    public Document(String document_name, String document_description, String document_comments, LocalDateTime add_date, LocalDateTime edition_date, byte[] content) {
        this.document_name = document_name;
        this.document_description = document_description;
        this.document_comments = document_comments;
        this.add_date = add_date;
        this.edition_date = edition_date;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getdocument_name() {
        return document_name;
    }

    public void setdocument_name(String document_name) {
        this.document_name = document_name;
    }

    public String getDocument_description() {
        return document_description;
    }

    public void setDocument_description(String document_description) {
        this.document_description = document_description;
    }

    public String getDocument_comments() {
        return document_comments;
    }

    public void setDocument_comments(String document_comments) {
        this.document_comments = document_comments;
    }

    public LocalDateTime getAdd_date() {
        return add_date;
    }

    public void setAdd_date() {
       LocalDateTime add_date = LocalDateTime.now();
        this.add_date = add_date;
    }

    public LocalDateTime getEdition_date() {
        return edition_date;
    }

    public void setEdition_date() {
        LocalDateTime edition_date = LocalDateTime.now();
       this.edition_date = edition_date;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", document_name='" + document_name + '\'' +
                ", document_description='" + document_description + '\'' +
                ", document_comments='" + document_comments + '\'' +
                ", add_date=" + add_date +
                ", edition_date=" + edition_date +
                ", content=" + content +
                '}';
    }
}
