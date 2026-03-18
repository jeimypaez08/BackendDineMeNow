package com.example.BackendDineMeNow.Dtos;

import com.example.BackendDineMeNow.models.Direccion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestauranteDto {
  private String id;
  private String NIT;
  private String razon_social;
  private String telefono;
  private String correo;
  private Direccion direccion;
  private String foto;
}
