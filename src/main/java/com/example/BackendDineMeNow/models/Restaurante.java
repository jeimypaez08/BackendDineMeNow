package com.example.BackendDineMeNow.models;

import java.time.LocalTime;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document (collection = "restaurantes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurante {
  @Id
  private String id;
  private String propietario; // ID del usuario asociado al restaurante
  private String nit;
  private String razonSocial;
  private String nombre;  
  private String descripcion;
  private int capacidad;
  private String categoria;
  private String telefono;
  private String correo;
  private Direccion direccion;
  private List<String> servicios;

  @JsonFormat(pattern = "HH:mm") // Agregar esta anotación para serializar el campo horarioApertura
  private LocalTime horarioApertura;
  @JsonFormat(pattern = "HH:mm") // Agregar esta anotación para serializar el campo horarioApertura
  private LocalTime horarioCierre;

  private List<String> diasAbierto;// Lista de días de la semana en los que el restaurante está abierto
  private String foto;
  private String urlRut;
  private String urlCamaraComercio;
  private EstadoRestaurante estado;// Estado del restaurante (activo, inactivo, pendiente etc.)
  private String password; // Contraseña para la autenticación del restaurante
  private Boolean mustChangePassword; // Indicador para saber si el restaurante debe cambiar su contraseña en el próximo inicio de sesión
}
