package com.example.BackendDineMeNow.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.EmpleadoDto;
import com.example.BackendDineMeNow.Mapper.EmpleadoMapper;
import com.example.BackendDineMeNow.models.Empleado;
import com.example.BackendDineMeNow.repositories.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{
    private final EmpleadoRepository empleadoRepository;

    private final EmpleadoMapper empleadoMapper;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository, EmpleadoMapper empleadoMapper) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoMapper = empleadoMapper;
    }

    @Override
    public EmpleadoDto crearEmpleado(EmpleadoDto empleadoDto) {
        Empleado empleado = empleadoMapper.toEmpleado(empleadoDto);
        return empleadoMapper.toEmpleadoDto(empleadoRepository.save(empleado));
    }

    @Override
    public List<EmpleadoDto> ListEmpleados() {
        return empleadoMapper.toEmpleadoDtoList(empleadoRepository.findAll());
    }

    @Override
    public EmpleadoDto actEmpleado(String id, EmpleadoDto empleadoDto) {
        Empleado empleado = empleadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Id de empleado no encontrado, id: " + id));
        empleadoMapper.actualizarEmpleado(empleadoDto, empleado);
        return empleadoMapper.toEmpleadoDto(empleadoRepository.save(empleado));
    }

    @Override
    public void borrarEmpleado(String id) {
        if (!empleadoRepository.existsById(id)) {
            throw new RuntimeException("Id de empleado no encontrado, id: " + id);
        }
        empleadoRepository.deleteById(id);
    }

}
