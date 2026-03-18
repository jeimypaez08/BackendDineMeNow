package com.example.BackendDineMeNow.Dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservaDto {
    //FrontEnd
    private String id;
    private String id_Cliente;
    private String id_Platos;
    private String id_Mesa;
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;
    private String estado;
}
