package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.EmpleadoDto;

public interface EmpleadoService {
    EmpleadoDto crearEmpleado(EmpleadoDto empleadoDto);

    List<EmpleadoDto> ListEmpleados();

    EmpleadoDto actEmpleado(String id, EmpleadoDto empleadoDto);

    void borrarEmpleado(String id);

}
