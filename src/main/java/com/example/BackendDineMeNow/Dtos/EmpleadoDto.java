package com.example.BackendDineMeNow.Dtos;

import java.util.List;

import com.example.BackendDineMeNow.models.Direccion;
import com.example.BackendDineMeNow.models.Documento;
import com.example.BackendDineMeNow.models.Rol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpleadoDto {
    private String id;
    private Documento documento;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String eps;
    private Direccion dirreccion;
    private List<Rol> rol;
    private String estado;
    private String foto;
    private String Id_Restaurante; //Posible objecto

}