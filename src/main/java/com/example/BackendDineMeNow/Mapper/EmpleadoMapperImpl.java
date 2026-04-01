package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.EmpleadoDto;
import com.example.BackendDineMeNow.models.Empleado;

@Component
public class EmpleadoMapperImpl implements EmpleadoMapper{// Implementación de la interfaz EmpleadoMapper para convertir entre entidades Empleado y DTOs EmpleadoDto
    @Override
    public Empleado toEmpleado(EmpleadoDto dto) {// Convertir un DTO EmpleadoDto a una entidad Empleado
        if (dto == null) {// Verificar si el DTO es nulo antes de intentar convertirlo
            return null;// Si el DTO es nulo, retornar null para evitar errores de NullPointerException
        }
        return Empleado.builder()// Construcción de la entidad Empleado a partir del DTO utilizando el patrón builder
            .id(dto.getId())
            .documento(dto.getDocumento())
            .nombre(dto.getNombre())
            .apellido(dto.getApellido())
            .telefono(dto.getTelefono())
            .correo(dto.getCorreo())
            .eps(dto.getEps())
            .direccion(dto.getDireccion())
            .rol(dto.getRol())
            .estado(dto.getEstado())
            .foto(dto.getFoto())
            .idRestaurante(dto.getIdRestaurante())
            .build();
    }

    @Override
    public EmpleadoDto toEmpleadoDto(Empleado empleado) {// Convertir una entidad Empleado a un DTO EmpleadoDto
        if (empleado == null) {
            return null;
        }
        return EmpleadoDto.builder()
            .id(empleado.getId())
            .documento(empleado.getDocumento())
            .nombre(empleado.getNombre())
            .apellido(empleado.getApellido())
            .telefono(empleado.getTelefono())
            .correo(empleado.getCorreo())
            .eps(empleado.getEps())
            .direccion(empleado.getDireccion())
            .rol(empleado.getRol())
            .estado(empleado.getEstado())
            .foto(empleado.getFoto())
            .idRestaurante(empleado.getIdRestaurante())
            .build();
    }

    @Override
    public List<EmpleadoDto> toEmpleadoDtoList(List<Empleado> empleados) {// Convertir una lista de entidades Empleado a una lista de DTOs EmpleadoDto
        if (empleados == null) return null; // Verificar si la lista de empleados es nula antes de intentar convertirla
        
        return empleados.stream()
            .map(this::toEmpleadoDto)
            .toList();
    }

    @Override
    public void actualizarEmpleado(EmpleadoDto empleadoDto, Empleado empleado) {// Actualizar una entidad Empleado existente con los valores de un DTO EmpleadoDto, verificando que no sean nulos
        if (empleado == null) 
            throw new IllegalArgumentException("El empleado esta vacio");
        
        if (empleadoDto == null) 
            throw new IllegalArgumentException("El dto del empleado esta vacio");
        
// Actualizar los campos del empleado con los valores del DTO, verificando que no sean nulos
        if (empleadoDto.getNombre() != null) empleado.setNombre(empleadoDto.getNombre());
        if (empleadoDto.getApellido() != null) empleado.setApellido(empleadoDto.getApellido());
        if (empleadoDto.getDocumento() != null) empleado.setDocumento(empleadoDto.getDocumento());
        if (empleadoDto.getDireccion() != null) empleado.setDireccion(empleadoDto.getDireccion());
        if (empleadoDto.getTelefono() != null) empleado.setTelefono(empleadoDto.getTelefono());
        if (empleadoDto.getCorreo() != null) empleado.setCorreo(empleadoDto.getCorreo());
        if (empleadoDto.getArl() != null) empleado.setArl(empleadoDto.getArl());
        if (empleadoDto.getEps() != null) empleado.setEps(empleadoDto.getEps());
        if (empleadoDto.getRol() != null) empleado.setRol(empleadoDto.getRol());
        if (empleadoDto.getEstado() != null) empleado.setEstado(empleadoDto.getEstado());
        if (empleadoDto.getFoto() != null) empleado.setFoto(empleadoDto.getFoto());
        if (empleadoDto.getIdRestaurante() != null) empleado.setIdRestaurante(empleadoDto.getIdRestaurante());
    }

}
