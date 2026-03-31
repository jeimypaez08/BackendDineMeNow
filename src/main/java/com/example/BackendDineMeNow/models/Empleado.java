package com.example.BackendDineMeNow.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "empleados")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Empleado {
    @Id
    private String id;
    private Documento documento;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String eps;
    private String arl;
    private Direccion direccion;
    private Rol rol; // Enum para definir el rol del empleado (CHEF, MESERO, etc.)
    private String estado; 
    private String foto;
    private String idRestaurante; //Posible objecto
}
