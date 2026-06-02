package com.example.BackendDineMeNow.Dtos;

import com.example.BackendDineMeNow.models.Direccion;
import com.example.BackendDineMeNow.models.Documento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDto {
    //FrontEnd
    private String id;
    
    private String nombreCliente;
    private String apellido;
    private Documento documento;
    private Direccion direccion;
    private String correo;
    private String telefono;
    private String foto;

}
