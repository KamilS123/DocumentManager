package com.kamil.DocumentManager.models;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Table(name = "user")
@Entity
public class User {
    @Column(name = "user_id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "name must be filled")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "surname must be filled")
    private String surname;

    @Column(name = "password")
    @NotBlank(message = "password must be filled")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "activationStatus")
    private String activationStatus;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<Document> documentSet = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private Set<AdminMessage> adminMessageSet;

    public Set<AdminMessage> getAdminMessageSet() {
        return adminMessageSet;
    }

    public void setAdminMessageSet(Set<AdminMessage> adminMessageSet) {
        this.adminMessageSet = adminMessageSet;
    }

    public Set<Document> getDocumentSet() {
        return documentSet;
    }

    public void setDocumentSet(Set<Document> documentSet) {
        this.documentSet = documentSet;
    }

    public User() {
    }

    public User(String name, String surname, String password, String status, String activationStatus) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.status = status;
        this.activationStatus = activationStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(String activationStatus) {
        this.activationStatus = activationStatus;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", activationStatus='" + activationStatus + '\'' +
                ", documentSet=" + documentSet +
                '}';
    }
}
