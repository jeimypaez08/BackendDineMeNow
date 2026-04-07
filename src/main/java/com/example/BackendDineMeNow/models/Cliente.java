package com.example.BackendDineMeNow.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    //BackEnd
    @Id
    private String id;
    private String nombreCliente;
    private String apellido;
    private Documento documento;
    private Direccion direccion;
    private String correo;
    private String telefono;
    private String foto;
    @Builder.Default
    private boolean verificado = false;

}
