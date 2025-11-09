package com.example.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Getter, Setter, toString, equals/hashCode otomatik üretir
@Builder // Builder deseni oluşturur
@NoArgsConstructor // Argümansız constructor
@AllArgsConstructor // Tüm argümanlarla constructor
public class LoginUserDTO {

    @NotBlank(message = "Kullanıcı adı veya e-posta boş bırakılamaz.")
    private String email; // Veya e-posta, genellikle ikisi de kabul edilir

    @NotBlank(message = "Parola boş bırakılamaz.")
    @Size(min = 8, message = "Parola uzunluğu yetersizdir.") // Girişte bile minimum karakter kontrolü iyi bir pratiktir.
    private String password;
}