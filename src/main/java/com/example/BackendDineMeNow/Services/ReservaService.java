package com.example.BackendDineMeNow.Services;

import java.time.LocalDate;
import java.util.List;

import com.example.BackendDineMeNow.Dtos.ReservaDto;

public interface ReservaService {

    ReservaDto crearReserva(ReservaDto reservaDto, String username);


    List<ReservaDto> listaReservas();
    List<ReservaDto> listarPorNit(String nitRestaurante);
    List<ReservaDto> listarPorFechaYnit(String nitRestaurante, LocalDate fecha);
    List<ReservaDto> listarPorCliente(String username);

    ReservaDto actReserva(String id, ReservaDto reservaDto);

    void borrarReserva(String id);

}
