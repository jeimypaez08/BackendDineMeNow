package com.example.BackendDineMeNow.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MesaDto {
    //FrontEnd
    private String id;
    private String nitRestaurante; //nit del restaurante al que pertenece la mesa
    private String numMesa;
    private int capacidad;
    private String estado;


}
