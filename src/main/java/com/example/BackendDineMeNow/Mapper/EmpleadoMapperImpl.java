package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.EmpleadoDto;
import com.example.BackendDineMeNow.models.Empleado;

@Component
public class EmpleadoMapperImpl implements EmpleadoMapper{
    @Override
    public Empleado toEmpleado(EmpleadoDto empleadoDto) {
        if (empleadoDto == null) {
            return null;
        }
        return Empleado.builder()
            .id(empleadoDto.getId())
            .doc(empleadoDto.getDocumento())
            .nom(empleadoDto.getNombre())
            .ape(empleadoDto.getApellido())
            .tel(empleadoDto.getTelefono())
            .email(empleadoDto.getCorreo())
            .eps(empleadoDto.getEps())
            .dir(empleadoDto.getDirreccion())
            .rol(empleadoDto.getRol())
            .state(empleadoDto.getEstado())
            .photo(empleadoDto.getFoto())
            .Id_Restaurant(empleadoDto.getId_Restaurante())
            .build();
    }

    @Override
    public EmpleadoDto toEmpleadoDto(Empleado empleado) {
        if (empleado == null) {
            return null;
        }
        return EmpleadoDto.builder()
            .id(empleado.getId())
            .documento(empleado.getDoc())
            .nombre(empleado.getNom())
            .apellido(empleado.getApe())
            .telefono(empleado.getTel())
            .correo(empleado.getEmail())
            .eps(empleado.getEps())
            .dirreccion(empleado.getDir())
            .rol(empleado.getRol())
            .estado(empleado.getState())
            .foto(empleado.getPhoto())
            .Id_Restaurante(empleado.getId_Restaurant())
            .build();
    }

    @Override
    public List<EmpleadoDto> toEmpleadoDtoList(List<Empleado> empleados) {
        if (empleados == null) {
            return null;
        }
        return empleados.stream()
            .map(this::toEmpleadoDto)
            .toList();
    }

    @Override
    public void actualizarEmpleado(EmpleadoDto empleadoDto, Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empledo esta vacio");
        }
        if (empleadoDto == null) {
            throw new IllegalArgumentException("El dto del empleado esta vacio");
        }

        empleado.setDoc(empleadoDto.getDocumento());
        empleado.setNom(empleadoDto.getNombre());
        empleado.setApe(empleadoDto.getApellido());
        empleado.setTel(empleadoDto.getTelefono());
        empleado.setEmail(empleadoDto.getCorreo());
        empleado.setEps(empleadoDto.getEps());
        empleado.setDir(empleadoDto.getDirreccion());
        empleado.setRol(empleadoDto.getRol());
        empleado.setState(empleadoDto.getEstado());
        empleado.setPhoto(empleadoDto.getFoto());
        empleado.setId_Restaurant(empleadoDto.getId_Restaurante());
    }

}
