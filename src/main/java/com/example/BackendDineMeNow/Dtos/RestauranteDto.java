package com.example.BackendDineMeNow.Dtos;

import java.time.LocalTime;
import java.util.List;

import com.example.BackendDineMeNow.models.Direccion;
import com.example.BackendDineMeNow.models.EstadoRestaurante;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ext.javatime.deser.LocalTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalTimeSerializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestauranteDto {
  private String id;
  private String propietario; // usuario asociado al restaurante
  private String nit;
  private String razonSocial;
  private String nombre;
  private String descripcion;
  private String categoria;
  private String telefono;
  private String correo;
  private Direccion direccion;

  @JsonFormat(pattern = "HH:mm:ss")
  @JsonSerialize(using = LocalTimeSerializer.class) // Agregar esta anotación para serializar el campo horarioApertura
  @JsonDeserialize(using = LocalTimeDeserializer.class) // Agregar esta anotación para serializar el campo horarioCierre
  private LocalTime horarioApertura;
  
  @JsonFormat(pattern = "HH:mm:ss")
  @JsonSerialize(using = LocalTimeSerializer.class) // Agregar esta anotación para serializar el campo horarioApertura
  @JsonDeserialize(using = LocalTimeDeserializer.class) // Agregar esta anotación para serializar el campo horarioCierre
  private LocalTime horarioCierre;
  
  private List<String> diasAbierto;// Lista de días de la semana en los que el restaurante está abierto
  private String foto;
  private EstadoRestaurante estado;
  private Boolean mustChangePassword; // Indicador para saber si el restaurante debe cambiar su contraseña en el próximo inicio de sesión
}
