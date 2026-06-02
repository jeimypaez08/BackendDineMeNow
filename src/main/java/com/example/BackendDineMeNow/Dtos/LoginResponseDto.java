package com.example.BackendDineMeNow.Dtos;

import java.util.List;

import com.example.BackendDineMeNow.models.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {
    private String mensaje;
    private String token;
    private String id;
    private String nombre;
    private String apellido;
    private String correo;
    private List<Rol> roles;
    private boolean mustChangePassword;



}
