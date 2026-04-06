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
      .propietario(restauranteDto.getPropietario())
      .nit(restauranteDto.getNit())
      .razonSocial(restauranteDto.getRazonSocial())
      .nombre(restauranteDto.getNombre())
      .descripcion(restauranteDto.getDescripcion())
      .capacidad(restauranteDto.getCapacidad())
      .categoria(restauranteDto.getCategoria())
      .telefono(restauranteDto.getTelefono())
      .correo(restauranteDto.getCorreo())
      .direccion(restauranteDto.getDireccion())
      .servicios(restauranteDto.getServicios())
      .horarioApertura(restauranteDto.getHorarioApertura())
      .horarioCierre(restauranteDto.getHorarioCierre())
      .diasAbierto(restauranteDto.getDiasAbierto())
      .foto(restauranteDto.getFoto())
      .urlRut(restauranteDto.getUrlRut())
      .urlCamaraComercio(restauranteDto.getUrlCamaraComercio())
      .estado(restauranteDto.getEstado())
      .mustChangePassword(restauranteDto.getMustChangePassword() != null && restauranteDto.getMustChangePassword())// Agregar el campo mustChangePassword al construir el objeto Restaurante
      .build();
  }
  @Override
public RestauranteDto toRestauranteDto (Restaurante restaurante){
  if(restaurante == null){
    return null;
  }
  return RestauranteDto.builder()
    .id(restaurante.getId())
    .propietario(restaurante.getPropietario())
    .nit(restaurante.getNit())
    .razonSocial(restaurante.getRazonSocial())
    .nombre(restaurante.getNombre())
    .descripcion(restaurante.getDescripcion())
    .capacidad(restaurante.getCapacidad())
    .categoria(restaurante.getCategoria())
    .telefono(restaurante.getTelefono())
    .correo(restaurante.getCorreo())
    .direccion(restaurante.getDireccion())
    .servicios(restaurante.getServicios())
    .horarioApertura(restaurante.getHorarioApertura())
    .horarioCierre(restaurante.getHorarioCierre())
    .diasAbierto(restaurante.getDiasAbierto())
    .foto(restaurante.getFoto())
    .urlRut(restaurante.getUrlRut())
    .urlCamaraComercio(restaurante.getUrlCamaraComercio())
    .estado(restaurante.getEstado())
    .mustChangePassword(restaurante.getMustChangePassword())// Agregar el campo mustChangePassword al construir el objeto RestauranteDto
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
    if (restauranteDto.getPropietario() != null) restaurante.setPropietario(restauranteDto.getPropietario());
    if (restauranteDto.getNit() != null) restaurante.setNit(restauranteDto.getNit());
    if (restauranteDto.getRazonSocial() != null) restaurante.setRazonSocial(restauranteDto.getRazonSocial());
    if (restauranteDto.getNombre() != null) restaurante.setNombre(restauranteDto.getNombre());
    if (restauranteDto.getCapacidad() != 0) restaurante.setCapacidad(restauranteDto.getCapacidad());
    if (restauranteDto.getDescripcion() != null) restaurante.setDescripcion(restauranteDto.getDescripcion());
    if (restauranteDto.getCategoria() != null) restaurante.setCategoria(restauranteDto.getCategoria());
    if (restauranteDto.getTelefono() != null) restaurante.setTelefono(restauranteDto.getTelefono());
    if (restauranteDto.getCorreo() != null) restaurante.setCorreo(restauranteDto.getCorreo());
    if (restauranteDto.getDireccion() != null) restaurante.setDireccion(restauranteDto.getDireccion());
    if (restauranteDto.getServicios() != null) restaurante.setServicios(restauranteDto.getServicios());
    if (restauranteDto.getHorarioApertura() != null) restaurante.setHorarioApertura(restauranteDto.getHorarioApertura());
    if (restauranteDto.getHorarioCierre() != null) restaurante.setHorarioCierre(restauranteDto.getHorarioCierre());
    if (restauranteDto.getDiasAbierto() != null) restaurante.setDiasAbierto(restauranteDto.getDiasAbierto());
    if (restauranteDto.getFoto() != null) restaurante.setFoto(restauranteDto.getFoto());
    if (restauranteDto.getEstado() != null) restaurante.setEstado(restauranteDto.getEstado());
    if (restauranteDto.getUrlRut() != null) restaurante.setUrlRut(restauranteDto.getUrlRut());
    if (restauranteDto.getUrlCamaraComercio() != null) restaurante.setUrlCamaraComercio(restauranteDto.getUrlCamaraComercio());
    
    
  }

}
