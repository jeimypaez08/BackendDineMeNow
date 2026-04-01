package com.example.BackendDineMeNow.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    private String identificador;// Puede ser correo electrónico o número de documento
    private String password;

}
