package com.example.banksystem.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Emails")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "user_Id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_Id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;
}
