package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.EmpleadoDto;
import com.example.BackendDineMeNow.models.Empleado;

public interface EmpleadoMapper {
    Empleado toEmpleado(EmpleadoDto empleadoDto);

    EmpleadoDto toEmpleadoDto(Empleado empleado);

    List<EmpleadoDto> toEmpleadoDtoList(List<Empleado> empleados);

    void actualizarEmpleado(EmpleadoDto empleadoDto, Empleado empleado);

}
