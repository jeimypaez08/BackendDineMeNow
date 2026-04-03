package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.PlatosDto;
import com.example.BackendDineMeNow.models.Platos;

public interface PlatosMapper {

    Platos toPlatos(PlatosDto platosDto); //Convertir PlatosDto a Platos

    PlatosDto toPlatosDto(Platos platos); //Convertir el Platos a PlatosDto

    List<PlatosDto> toPlatosDtoList(List<Platos> platos); //Convertir una lista de Platos a una lista de PlatosDto

    void actualizarPlatos(PlatosDto platosDto, Platos platos); //Actualizar un plato
}
