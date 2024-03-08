package com.example.banksystem.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "first_Name")
    private String firstName;
    @Column(name = "avg_Name")
    private String avgName;
    @Column(name = "last_Name")
    private String lastName;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "date_Born")
    private String dateBorn;
    @Column(name = "number_Account")
    private String numberAccount;
    @Column(name = "money")
    private double money;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Collection<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Phone> phones;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Email> emails;
}
