package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.MesaDto;

public interface MesaService {

    MesaDto crearMesa(MesaDto mesaDto);

    List<MesaDto> ListaMesas();

    MesaDto actMesa(String id, MesaDto mesaDto);

    void borrarMesa(String id);

}
