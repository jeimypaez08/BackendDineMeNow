package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.ReservaDto;
import com.example.BackendDineMeNow.models.Reserva;

public interface ReservaMapper {

    Reserva toReserva(ReservaDto reservaDto); //Convertir ReservaDto a Reserva

    ReservaDto toReservaDto(Reserva reserva); //Convertir Reserva a ReservaDto
    
    List<ReservaDto> toReservaDtoList(List<Reserva> reservas);

    void actualizarReserva(ReservaDto reservaDto, Reserva reserva);
}
