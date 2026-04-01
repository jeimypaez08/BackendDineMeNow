package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.RestauranteDto;
import com.example.BackendDineMeNow.models.Restaurante;

public interface RestauranteMapper {

  Restaurante toRestaurante (RestauranteDto restauranteDto);

  RestauranteDto toRestauranteDto (Restaurante restaurante);

  List<RestauranteDto> toRestauranteDtoList(List<Restaurante> restaurantes);

  void actualizarRestaurante(RestauranteDto restauranteDto, Restaurante restaurante);
}
