package com.example.BackendDineMeNow.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitarRecuperacionDto {
    private String correo;
    private String rol; // Puede ser "CLIENTE" o "RESTAURANTE"
}
