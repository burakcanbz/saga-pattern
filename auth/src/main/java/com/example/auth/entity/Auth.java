package com.example.auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@Table(name="auth")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    private String username;

    @Column(nullable = false)
    private String hashedPassword;

}
