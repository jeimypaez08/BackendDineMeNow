package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.PlatosDto;

public interface  PlatosService {

    PlatosDto crearPlato(PlatosDto platoDto);
    
    List<PlatosDto> ListaPlatos();
    
    PlatosDto actPlato(String id, PlatosDto platoDto);

    void borrarPlato(String id);

    PlatosDto getPlatoById(String id);

}
