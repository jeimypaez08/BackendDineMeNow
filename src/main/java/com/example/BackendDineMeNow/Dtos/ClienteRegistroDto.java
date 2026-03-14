package com.example.BackendDineMeNow.Dtos;

import java.util.List;

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
public class ClienteRegistroDto {
    private String nombre;
    private String apellido;
    private Documento documento;
    private Direccion direccion;
    private String email;
    private String user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private List<Rol> roles;

}
