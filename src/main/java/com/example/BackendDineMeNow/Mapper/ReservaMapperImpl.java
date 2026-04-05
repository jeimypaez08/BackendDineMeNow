package com.example.BackendDineMeNow.Mapper;


import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.ReservaDto;
import com.example.BackendDineMeNow.models.EstadoReserva;
import com.example.BackendDineMeNow.models.Reserva;

@Component
public class ReservaMapperImpl implements ReservaMapper{

    @Override
    public Reserva toReserva(ReservaDto reservaDto) {
        if (reservaDto == null) {
            return null;
        }
        return Reserva.builder()
                .id(reservaDto.getId())
                .nitRestaurante(reservaDto.getNitRestaurante())
                .nombreCliente(reservaDto.getNombreCliente())
                .nomPlato(reservaDto.getNombrePlatos())
                .numMesa(reservaDto.getNumeroMesa())
                .fecha(reservaDto.getFecha())
                .hora(reservaDto.getHora())
                .descrip(reservaDto.getDescripcion())
                .estado(reservaDto.getEstado() !=null ?// Verificar si el estado no es nulo antes de intentar convertirlo a un valor de la enumeración EstadoReserva
                        EstadoReserva.valueOf(reservaDto.getEstado().toUpperCase()):// Si el estado no es nulo, se convierte a un valor de la enumeración EstadoReserva, de lo contrario se asigna un valor por defecto
                        EstadoReserva.PENDIENTE)// Si el estado es nulo, se asigna un valor por defecto (PENDIENTE) para evitar errores de NullPointerException
                .build();
    }

    @Override
    public ReservaDto toReservaDto(Reserva reserva){
        if (reserva == null) {
            return null;
        }
        return ReservaDto.builder()
                .id(reserva.getId())
                .nitRestaurante(reserva.getNitRestaurante())
                .nombreCliente(reserva.getNombreCliente())
                .nombrePlatos(reserva.getNomPlato())
                .numeroMesa(reserva.getNumMesa())
                .fecha(reserva.getFecha())
                .hora(reserva.getHora())
                .descripcion(reserva.getDescrip())
                .estado(reserva.getEstado() != null ? reserva.getEstado().name() : null)// Verificar si el estado no es nulo antes de intentar convertirlo a una cadena, de lo contrario se asigna null
                .build();
    }

    //Lista de reservas
    @Override
    public List<ReservaDto> toReservaDtoList(List<Reserva> reservas){
        if (reservas == null) return null;// Verificar si la lista de reservas es nula antes de intentar convertirla, de lo contrario se asigna null para evitar errores de NullPointerException
            return reservas.stream().map(this::toReservaDto).toList();// Convertir cada reserva de la lista a un DTO utilizando el método toReservaDto y recopilar los resultados en una nueva lista de DTOs

    }

    @Override
    public void actualizarReserva(ReservaDto reservaDto, Reserva reserva) {
        if (reserva == null) {
            throw new IllegalArgumentException("La reserva esta vacia");
        }
        if (reservaDto == null) {
            throw new IllegalArgumentException("El dto de la reserva esta vacio");
        }
        
        //Actualizar la entidad
        reserva.setNombreCliente(reservaDto.getNombreCliente());
        reserva.setNomPlato(reservaDto.getNombrePlatos());
        reserva.setNumMesa(reservaDto.getNumeroMesa());
        reserva.setFecha(reservaDto.getFecha());
        reserva.setHora(reservaDto.getHora());
        reserva.setDescrip(reservaDto.getDescripcion());
        if(reservaDto.getEstado() != null){// Verificar si el estado no es nulo antes de intentar convertirlo a un valor de la enumeración EstadoReserva
        reserva.setEstado(EstadoReserva.valueOf(reservaDto.getEstado().toUpperCase()));// Si el estado no es nulo, se convierte a un valor de la enumeración EstadoReserva y se asigna a la reserva, de lo contrario no se actualiza el estado para evitar errores de NullPointerException
        }
    }
}
