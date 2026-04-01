package com.example.BackendDineMeNow.Mapper;


import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.ReservaDto;
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
                .nombreCliente(reservaDto.getNombreCliente())
                .nombrePlatos(reservaDto.getNombrePlatos())
                .numeroMesa(reservaDto.getNumeroMesa())
                .fecha(reservaDto.getFecha())
                .hora(reservaDto.getHora())
                .descrip(reservaDto.getDescripcion())
                .estado(Boolean.parseBoolean(reservaDto.getEstado()))
                .build();
    }

    @Override
    public ReservaDto toReservaDto(Reserva reserva){
        if (reserva == null) {
            return null;
        }
        return ReservaDto.builder()
                .id(reserva.getId())
                .nombreCliente(reserva.getNombreCliente())
                .nombrePlatos(reserva.getNombrePlatos())
                .numeroMesa(reserva.getNumeroMesa())
                .fecha(reserva.getFecha())
                .hora(reserva.getHora())
                .descripcion(reserva.getDescrip())
                .estado(String.valueOf(reserva.isEstado()))
                .build();
    }

    //Lista de reservas
    @Override
    public List<ReservaDto> toReservaDtoList(List<Reserva> reservas){
        if (reservas == null) {
            return null;
        }

        return reservas.stream()
                .map(this::toReservaDto)
                .toList();
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
        reserva.setNombrePlatos(reservaDto.getNombrePlatos());
        reserva.setNumeroMesa(reservaDto.getNumeroMesa());
        reserva.setFecha(reservaDto.getFecha());
        reserva.setHora(reservaDto.getHora());
        reserva.setDescrip(reservaDto.getDescripcion());
        reserva.setEstado(Boolean.parseBoolean(reservaDto.getEstado()));
    }
}
