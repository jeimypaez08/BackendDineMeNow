package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.MesaDto;
import com.example.BackendDineMeNow.models.Mesa;

public interface MesaMapper {

    Mesa toMesa(MesaDto mesaDto); //Convertir MesaDto a Mesa

    MesaDto toMesaDto(Mesa mesa); //Convertir Mesa a MesaDto

    List<MesaDto> toMesaDtoList(List<Mesa> mesas);

        void actualizarMesa(MesaDto mesaDto, Mesa mesa);

}
