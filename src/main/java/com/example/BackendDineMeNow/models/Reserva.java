package com.example.BackendDineMeNow.models;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Reserva")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reserva {
    //BackEnd
    @Id
    private String id;
    private String nombreCliente;
    private String nombrePlatos;
    private String numeroMesa;
    private LocalDate fecha;
    private LocalTime hora;
    private String descrip;
    private boolean estado;
}
