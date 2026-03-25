package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.ChefDto;

public interface ChefService {

    ChefDto crearChef(ChefDto chefDto);
    List<ChefDto> obtenerTodosLosChefs();
    ChefDto actualizarChef(String id, ChefDto chefDto);
    void eliminarChef(String id);
}
