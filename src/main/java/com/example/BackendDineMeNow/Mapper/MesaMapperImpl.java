package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.MesaDto;
import com.example.BackendDineMeNow.models.Mesa;

@Component
public class MesaMapperImpl implements MesaMapper{

    @Override
    public Mesa toMesa(MesaDto mesaDto){
        if (mesaDto == null) {
            return null;
        }
        return Mesa.builder()
                .id(mesaDto.getId())
                .nitRestaurante(mesaDto.getNitRestaurante())
                .numMesa(mesaDto.getNumMesa())
                .capacidad(mesaDto.getCapacidad())
                .estado(Boolean.parseBoolean(mesaDto.getEstado()))
                .build();
    }

    @Override
    public MesaDto toMesaDto(Mesa mesa){
        if (mesa == null) {
            return null;
        }
        return MesaDto.builder()
                .id(mesa.getId())
                .nitRestaurante(mesa.getNitRestaurante())
                .numMesa(mesa.getNumMesa())
                .capacidad(mesa.getCapacidad())
                .estado(String.valueOf(mesa.isEstado()))
                .build();

    }

    //Lista de mesas
    @Override
    public List<MesaDto> toMesaDtoList(List<Mesa> mesas){
        if (mesas == null) {
            return null;
        }

        return mesas.stream()
                .map(this::toMesaDto)
                .toList();
    }

    //Actualizar mesa existente con datos del DTO
    @Override
    public void actualizarMesa(MesaDto mesaDto, Mesa mesa){
        if (mesa == null){
            throw new IllegalArgumentException("La mesa no puede estar vacia");
        }
        if (mesaDto == null){
            throw new IllegalArgumentException("La mesaDto no puede estar vacia");
        }

        // Actualizar solo los campos que no son nulos en el DTO   
        mesa.setNumMesa(mesaDto.getNumMesa());
        mesa.setCapacidad(mesaDto.getCapacidad());
        mesa.setEstado(Boolean.parseBoolean(mesaDto.getEstado()));
    }   
}
