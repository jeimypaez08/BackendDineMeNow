package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.RestauranteDto;

public interface RestauranteService {
  RestauranteDto crearRestaurante (RestauranteDto restauranteDto);
  List<RestauranteDto> ListRestaurante();
  RestauranteDto actualizarRestaurante(String id, RestauranteDto restauranteDto);
  void borrarRestaurante(String id);
}
