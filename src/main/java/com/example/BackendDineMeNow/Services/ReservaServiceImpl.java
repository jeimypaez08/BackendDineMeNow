package com.example.BackendDineMeNow.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.ReservaDto;
import com.example.BackendDineMeNow.Mapper.ReservaMapper;
import com.example.BackendDineMeNow.models.EstadoReserva;
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
        //validar si la mesa ya esta ocupada, si la fecha y hora ya esta ocupada, etc.
        boolean mesaOcupada = reservaRepository.existsByNitRestauranteAndNumMesaAndFechaAndHora(
                reservaDto.getNitRestaurante(), 
                reservaDto.getNumeroMesa(), 
                reservaDto.getFecha(), 
                reservaDto.getHora()
            );

        if (mesaOcupada) {
            throw new RuntimeException("La mesa " + reservaDto.getNumeroMesa() + " ya está ocupada en la fecha y hora especificadas");
        }

        //2.convertir el dto a modelo
        Reserva reserva = reservaMapper.toReserva(reservaDto);

        //3. forzar estado inicial a "pendiente" por si el frontend no lo envia o envia otro estado
        if(reserva.getEstado() == null){//si el estado no es enviado por el frontend, se asigna el estado pendiente por defecto
            reserva.setEstado(EstadoReserva.PENDIENTE);
        }

        //4. guardar en la base de datos
        Reserva reservaGuardada = reservaRepository.save(reserva);
        return reservaMapper.toReservaDto(reservaGuardada);
    }

    // Listar reservas
    @Override
    public List<ReservaDto> listaReservas() {
        return reservaMapper.toReservaDtoList(reservaRepository.findAll());
    }

    //listar reservas por nit del restaurante
    @Override
    public List<ReservaDto> listarPorNit(String nitRestaurante) {
        return reservaMapper.toReservaDtoList(reservaRepository.findByNitRestaurante(nitRestaurante));
    }

    //listar por fecha y nit del restaurante
    @Override
    public List<ReservaDto> listarPorFechaYnit(String nitRestaurante, LocalDate fecha) {
        return reservaMapper.toReservaDtoList(reservaRepository.findByNitRestauranteAndFecha(nitRestaurante, fecha));
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