package com.example.BackendDineMeNow.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document (collection = "Restaurante")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurante {
  @Id
  private String id;
  private String NIT;
  private String razon_social;
  private String telefono;
  private String correo;
  private Direccion direccion;
  private String foto;
}
