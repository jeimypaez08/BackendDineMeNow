package com.example.BackendDineMeNow.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Empleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Empleado {
    @Id
    private String id;
    private Documento doc;
    private String nom;
    private String ape;
    private String tel;
    private String email;
    private String eps;
    private Direccion dir;
    private List<Rol> rol;
    private String state;
    private String photo;
    private String Id_Restaurant; //Posible objecto
}
