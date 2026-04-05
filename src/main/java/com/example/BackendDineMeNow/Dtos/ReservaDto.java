package com.example.BackendDineMeNow.Dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


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
    private String nitRestaurante;
    private String nombreCliente;//nombre del cliente que hizo la reserva, se puede cambiar a una referencia al cliente si se desea, telefono o correo para contacto
    private List<String> nombrePlatos;//nombre del plato reservado, se puede cambiar a una lista si se permite reservar varios platos
    private String numeroMesa;
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;
    private String estado;
}