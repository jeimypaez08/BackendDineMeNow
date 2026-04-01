package com.example.BackendDineMeNow.Dtos;

import java.util.List;

import com.example.BackendDineMeNow.models.Categorias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlatosDto {

    private String id;
    private String nombrePlatos;
    private String descripcion;
    private double precio;
    private List<Categorias> categoria;
}
