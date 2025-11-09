package com.example.auth.dto;

import jakarta.validation.constraints.Email; // Javax/jakarta'ya dikkat edin
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {

    @NotBlank(message = "Kullanıcı adı boş bırakılamaz.")
    @Size(min = 3, max = 50, message = "Kullanıcı adı 3 ile 50 karakter arasında olmalıdır.")
    private String username;

    @NotBlank(message = "E-posta boş bırakılamaz.")
    @Email(message = "Geçerli bir e-posta adresi giriniz.")
    private String email;

    // Not: DTO'ya dışarıdan gelen parola HASH'lenmemiş olmalıdır.
    @NotBlank(message = "Parola boş bırakılamaz.")
    @Size(min = 8, message = "Parola en az 8 karakter olmalıdır.")
    private String password;
}