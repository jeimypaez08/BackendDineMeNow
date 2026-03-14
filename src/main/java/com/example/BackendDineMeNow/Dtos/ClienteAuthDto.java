package com.example.BackendDineMeNow.Dtos;

import java.util.List;

import com.example.BackendDineMeNow.models.Rol;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteAuthDto {
    private String id;
    private String user;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password; //Solo leer la contra

    private List<Rol> roles;

}
