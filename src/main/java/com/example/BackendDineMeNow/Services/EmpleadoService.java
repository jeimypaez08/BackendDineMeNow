package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.EmpleadoDto;
import com.example.BackendDineMeNow.Dtos.EmpleadoRegistroDto;

public interface EmpleadoService {
    EmpleadoRegistroDto registrarEmpleado(EmpleadoRegistroDto dto);

    List<EmpleadoDto> listarEmpleados();
    List<EmpleadoDto> listarPorRestaurante(String idRestaurante);

    EmpleadoDto obtenerPorId(String id);
    EmpleadoDto obtenerPorDocumento(String numero);
    EmpleadoDto actualizarEmpleado(String id, EmpleadoDto dto);
    EmpleadoDto actualizarPorDocumento(String numero, EmpleadoDto dto);

    void eliminarEmpleado(String id);
    void eliminarPorDocumento(String numero);

}
