package com.example.BackendDineMeNow.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    //BackEnd
    @Id
    private String id;
    private String nom;
    private String ape;
    private Documento doc;
    private Direccion dir;
    private String correo;

}
