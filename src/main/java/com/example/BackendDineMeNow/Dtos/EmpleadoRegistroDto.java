package com.example.BackendDineMeNow.Dtos;


import com.example.BackendDineMeNow.models.Direccion;
import com.example.BackendDineMeNow.models.Documento;
import com.example.BackendDineMeNow.models.Rol;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpleadoRegistroDto {
    private Documento documento;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String eps;
    private String arl;
    private Direccion direccion;
    private Rol rol; // Enum para definir el rol del empleado (CHEF, MESERO
    private String estado;
    private String foto;
    private String idRestaurante; //Posible objecto
    private String user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
