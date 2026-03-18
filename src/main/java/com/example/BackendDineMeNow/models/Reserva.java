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
    private String id_Cliente;
    private String id_Platos;
    private String id_Mesa;
    private LocalDate fecha;
    private LocalTime hora;
    private String descrip;
    private String estado;
}
