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
public class Usuario {
    //BackEnd
    @Id
    private String id;
    private String nom;
    private String ape;
    private String correo;

}
