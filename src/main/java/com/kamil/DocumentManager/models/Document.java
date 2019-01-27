package com.kamil.DocumentManager.models;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "document_name")
    private String documetn_name;

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

    public Document() {
    }

    public Document(String documetn_name, String document_description, String document_comments, LocalDateTime add_date, LocalDateTime edition_date, byte[] content) {
        this.documetn_name = documetn_name;
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

    public String getDocumetn_name() {
        return documetn_name;
    }

    public void setDocumetn_name(String documetn_name) {
        this.documetn_name = documetn_name;
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

    public void setAdd_date(LocalDateTime add_date) {
        String date = "2019-01-27 01:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
        add_date = LocalDateTime.parse(date,formatter);
        this.add_date = add_date;
    }

    public LocalDateTime getEdition_date() {
        return edition_date;
    }

    public void setEdition_date(LocalDateTime edition_date) {
        String date = "2019-01-27 01:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-LL-dd HH:mm:ss");
        edition_date = LocalDateTime.parse(date,formatter);
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
                ", documetn_name='" + documetn_name + '\'' +
                ", document_description='" + document_description + '\'' +
                ", document_comments='" + document_comments + '\'' +
                ", add_date=" + add_date +
                ", edition_date=" + edition_date +
                ", content=" + content +
                '}';
    }
}
