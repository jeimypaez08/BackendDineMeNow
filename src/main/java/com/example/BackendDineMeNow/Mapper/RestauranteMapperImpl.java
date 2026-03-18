package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.RestauranteDto;
import com.example.BackendDineMeNow.models.Restaurante;


@Component
public class RestauranteMapperImpl implements RestauranteMapper{
  @Override
  public Restaurante toRestaurante (RestauranteDto restauranteDto){
    if (restauranteDto == null) {
      return null;
    }
    return Restaurante.builder()
      .id(restauranteDto.getId())
      .NIT(restauranteDto.getNIT())
      .razon_social(restauranteDto.getRazon_social())
      .telefono(restauranteDto.getTelefono())
      .correo(restauranteDto.getCorreo())
      .direccion(restauranteDto.getDireccion())
      .foto(restauranteDto.getFoto())
      .build();
  }
  @Override
public RestauranteDto toRestauranteDto (Restaurante restaurante){
  if(restaurante == null){
    return null;
  }
  return RestauranteDto.builder()
    .id(restaurante.getId())
    .NIT(restaurante.getNIT())
    .razon_social(restaurante.getRazon_social())
    .telefono(restaurante.getTelefono())
    .correo(restaurante.getCorreo())
    .direccion(restaurante.getDireccion())
    .foto(restaurante.getFoto())
    .build();
}
  @Override
  public List<RestauranteDto>toRestauranteDtoList(List<Restaurante>restaurante){
    if (restaurante == null) {
      return null;
    }
    return restaurante.stream()
      .map(this::toRestauranteDto)
      .toList();
  }
  @Override
  public void actualizarRestaurante(RestauranteDto restauranteDto, Restaurante restaurante){
    if (restaurante == null) {
      throw new IllegalArgumentException("El Restaurante esta vacio");
    }
    if (restauranteDto == null) {
      throw new IllegalArgumentException("El dto del Restaurante esta vacio");
    }
    restaurante.setNIT(restauranteDto.getNIT());
    restaurante.setRazon_social(restauranteDto.getRazon_social());
    restaurante.setTelefono(restauranteDto.getTelefono());
    restaurante.setCorreo(restauranteDto.getCorreo());
    restaurante.setDireccion(restauranteDto.getDireccion());
    restaurante.setFoto(restauranteDto.getFoto());
  }
}
