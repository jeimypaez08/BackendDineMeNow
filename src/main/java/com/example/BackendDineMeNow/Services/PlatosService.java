package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.PlatosDto;

public interface  PlatosService {

    PlatosDto crearPlatos(PlatosDto platoDto);
    
    List<PlatosDto> ListaPlatos();
    
    PlatosDto actPlatos(String id, PlatosDto platoDto);

    void borrarPlatos(String id);

}
