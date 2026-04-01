package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.LoginRequestDto;
import com.example.BackendDineMeNow.Dtos.RestauranteDto;
import com.example.BackendDineMeNow.models.EstadoRestaurante;

public interface RestauranteService {

  RestauranteDto crearRestaurante (RestauranteDto restauranteDto);

  RestauranteDto login(LoginRequestDto loginDto);

  List<RestauranteDto> listarRestaurante();
  List<RestauranteDto> listarPorEstado(EstadoRestaurante estado);
  List<RestauranteDto> listarPorCategoria(String categoria);

  RestauranteDto obtenerPorId(String id);
  RestauranteDto obtenerPorNit(String nit);

  RestauranteDto actualizarRestaurante(String id, RestauranteDto restauranteDto);
  RestauranteDto actualizarPorNit(String nit, RestauranteDto restauranteDto);

  void cambiarPassword(String nit, String passwordActual, String passwordNueva);
  void cambiarPasswordid(String id, String passwordActual, String passwordNueva);

  RestauranteDto aprobarRestaurante(String nit);
  RestauranteDto rechazarRestaurante(String nit);

  void eliminarRestaurante(String id);
  void eliminarPorNit(String nit);
}
