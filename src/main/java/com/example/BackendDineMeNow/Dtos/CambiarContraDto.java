package com.example.BackendDineMeNow.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Anotación de Lombok para generar getters, setters, toString, equals y hashCode automáticamente
@AllArgsConstructor // Anotación de Lombok para generar un constructor con todos los campos como parámetros
@NoArgsConstructor // Anotación de Lombok para generar un constructor sin parámetros
public class CambiarContraDto {

    private String passwordActual; // Campo para la contraseña actual del restaurante
    private String passwordNueva; // Campo para la nueva contraseña que se desea establecer
}
