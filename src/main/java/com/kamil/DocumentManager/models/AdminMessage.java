package com.kamil.DocumentManager.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "adminMessage")
public class AdminMessage {
    @Id
    @GeneratedValue
    @Column(name = "adminMessage_id")
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public AdminMessage(String message) {
        this.message = message;
    }

    public AdminMessage() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AdminMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
