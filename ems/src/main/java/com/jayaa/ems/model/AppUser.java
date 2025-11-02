package com.jayaa.ems.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(nullable = false)
    private String password;  // Stored as BCrypt hash

    @Column(nullable = false, length = 50)
    private String role;  // ROLE_USER, ROLE_ADMIN

    @Column(nullable = false)
    private Boolean enabled = true;
}