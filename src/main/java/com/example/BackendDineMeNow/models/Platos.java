package com.example.BackendDineMeNow.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Platos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Platos {
//Modelo es para el sistema
//BackEnd
    private String idplatos;
    private String nomPlatos;
    private String desc;
    private double precio;
    private List<Categorias> categ; //Lista de categorias
}
