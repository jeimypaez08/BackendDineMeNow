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
    private String numeroMesa;
    private int capacidad;
    private String estado;


}
