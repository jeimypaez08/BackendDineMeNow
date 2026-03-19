package com.example.BackendDineMeNow.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "mesas")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Mesa {
    @Id
    private String id;
    private String num_mesa;
    private int capacidad; // numero de personas que pueden sentarse en la mesa
    private boolean estado; //mesa está disponible para reservar o no

}
