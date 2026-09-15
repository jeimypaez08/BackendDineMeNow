package com.example.BackendDineMeNow.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificarCodigoRecuperacionDto {
    private String correo;
    private String codigo;
}
