package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.ReservaDto;

public interface ReservaService {

    ReservaDto crearReserva(ReservaDto reservaDto);

    List<ReservaDto> ListaReservas();

    ReservaDto actReserva(String id, ReservaDto reservaDto);

        void borrarReserva(String id);

        ReservaDto obtenerReservaPorId(String id);

}
