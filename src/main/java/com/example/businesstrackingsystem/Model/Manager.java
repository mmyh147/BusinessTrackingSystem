package com.example.businesstrackingsystem.Model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(columnDefinition = "varchar(15) not null unique")
    private String name;

    @NotEmpty
    @Email
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;
    private LocalDateTime registrationDate;
}
