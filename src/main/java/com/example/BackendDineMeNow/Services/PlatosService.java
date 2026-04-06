package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.PlatosDto;
import com.example.BackendDineMeNow.models.Categorias;

public interface  PlatosService {

    PlatosDto crearPlatos(PlatosDto platoDto);
    
    List<PlatosDto> listarPlatos();
    List<PlatosDto> listarPorRestaurante(String nitRestaurante);
    List<PlatosDto> listarPorNombre(String nomPlato);
    List<PlatosDto> listarPorCategoria(List<Categorias> categ);
    List<PlatosDto> listarPorNombreYPrecioMax(String nomPlato, double precioMax);
    
    PlatosDto actPlatos(String id, PlatosDto platoDto);
    PlatosDto actualizarPlatosPorNitRestauranteAndNombre(String nitRestaurante, String nomPlato, PlatosDto platoDto);

    void borrarPlatos(String id);
    void borrarPlatosPorNitRestauranteAndNombre(String nitRestaurante, String nomPlato);

}
