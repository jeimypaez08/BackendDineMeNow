package com.example.BackendDineMeNow.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.ReservaDto;
import com.example.BackendDineMeNow.Mapper.ReservaMapper;
import com.example.BackendDineMeNow.models.Reserva;
import com.example.BackendDineMeNow.repositories.ReservaRepository;

@Service
public class ReservaServiceImpl implements ReservaService {
    private final ReservaRepository reservaRepository;

    private final ReservaMapper reservaMapper;

    public ReservaServiceImpl(ReservaRepository reservaRepository, ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
    }

    // Crear reserva
    @Override
    public ReservaDto crearReserva(ReservaDto reservaDto) {
        Reserva reserva = reservaMapper.toReserva(reservaDto);
        return reservaMapper.toReservaDto(reservaRepository.save(reserva));
    }

    // Listar reservas
    @Override
    public List<ReservaDto> ListaReservas() {
        return reservaMapper.toReservaDtoList(reservaRepository.findAll());
    }

    // Actualizar reserva
    @Override
    public ReservaDto actReserva(String id, ReservaDto reservaDto) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id de reserva no encontrado, id: " + id));
        reservaMapper.actualizarReserva(reservaDto, reserva);
        return reservaMapper.toReservaDto(reservaRepository.save(reserva));
    }

    // Eliminar reserva
    @Override
    public void borrarReserva(String id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Id de reserva no encontrado, id: " + id);
        }
        reservaRepository.deleteById(id);
    }

}