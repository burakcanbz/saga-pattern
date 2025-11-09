package com.example.auth.service;

import com.example.auth.dto.LoginUserDTO;
import com.example.auth.dto.RegisterUserDTO;
import com.example.auth.entity.Auth;
import com.example.auth.repository.AuthRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public Auth registerUser(RegisterUserDTO registerUserDTO) {
        if (authRepository.findByEmail(registerUserDTO.getEmail()).isPresent()) {
            throw new RuntimeException("E mail already in used");
        }

        Auth newUser = Auth.builder()
                .username(registerUserDTO.getUsername())
                .email(registerUserDTO.getEmail())
                .hashedPassword(passwordEncoder.encode(registerUserDTO.getPassword()))
                .build();

       return authRepository.save(newUser);

    }

    public Auth loginUser(LoginUserDTO loginUserDTO, HttpServletResponse response) {
        Auth user = authRepository.findByEmail(loginUserDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("No e-mail found."));

        if(!passwordEncoder.matches(loginUserDTO.getPassword(), user.getHashedPassword())) {
            throw new IllegalStateException("Wrong password.");
        }

        String token = jwtService.generateToken(user);
        Cookie cookie = new Cookie("auth-token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);

        return user;
    }

}