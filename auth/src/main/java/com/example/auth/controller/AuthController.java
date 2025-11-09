package com.example.auth.controller;

import com.example.auth.dto.LoginUserDTO;
import com.example.auth.dto.RegisterUserDTO;
import com.example.auth.entity.Auth;
import com.example.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid; // Kullanıcı verilerini doğrulamak için (isteğe bağlı)

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        try{
            Auth savedUser = authService.registerUser(registerUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser.getEmail() + " created");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginUserDTO loginUserDTO, HttpServletResponse response) {
        try{
            Auth user = authService.loginUser(loginUserDTO, response);
            return ResponseEntity.status(HttpStatus.CREATED).body(user.getEmail() + " logged in");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}