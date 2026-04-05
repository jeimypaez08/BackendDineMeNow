package com.example.BackendDineMeNow.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
    private String nitRestaurante;
    private String nombreCliente;
    private List<String> nomPlato;
    private String numMesa;
    private LocalDate fecha;
    private LocalTime hora;
    private String descrip;
    private EstadoReserva estado;
}
