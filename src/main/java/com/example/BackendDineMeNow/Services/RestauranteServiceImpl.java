package com.example.BackendDineMeNow.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.RestauranteDto;
import com.example.BackendDineMeNow.Mapper.RestauranteMapper;
import com.example.BackendDineMeNow.models.Restaurante;
import com.example.BackendDineMeNow.repositories.RestauranteRepository;

@Service
public class RestauranteServiceImpl implements RestauranteService {
  private final RestauranteRepository restauranteRepository;

  private final RestauranteMapper restauranteMapper; 
  public RestauranteServiceImpl (RestauranteRepository restauranteRepository, RestauranteMapper restauranteMapper){
    this.restauranteRepository = restauranteRepository;
    this.restauranteMapper = restauranteMapper;
  }
//Crear un Restaurante
@Override
public RestauranteDto crearRestaurante (RestauranteDto restauranteDto){
  Restaurante restaurante = restauranteMapper.toRestaurante(restauranteDto);
  return restauranteMapper.toRestauranteDto(restauranteRepository.save(restaurante));
}
//listar restaurantes 
@Override
public List<RestauranteDto> ListRestaurante(){
  return restauranteMapper.toRestauranteDtoList(restauranteRepository.findAll());
}
//Actualizar restaurante

@Override
public RestauranteDto actualizarRestaurante(String id, RestauranteDto restauranteDto){
  Restaurante restaurante = restauranteRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Id de restaurante no encontrado, id: " + id));
    restauranteMapper.actualizarRestaurante(RestauranteDto restauranteDto);
    return restauranteMapper.toRestauranteDto(restauranteRepository.save(restaurante));  
}
//Eliminar
@Override
public void borrarRestaurante(String id){
  if (!restauranteRepository.existsById(id)) {
    throw new RuntimeException("Id de restaurante no encontrado, id: " + id);
  }
  restauranteRepository.deleteById(id);
}
}
