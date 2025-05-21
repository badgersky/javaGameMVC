package com.example.javagamemvc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

enum accountType{admin, user};

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private accountType accountType;
    private String username;
    private String email;
}