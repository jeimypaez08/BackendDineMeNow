package com.example.BackendDineMeNow.models;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "administradores")
@Data
public class Admin {

    @Id
    private String id;
    private String correo;
    private String password;
    private Set<String> rol;

}
