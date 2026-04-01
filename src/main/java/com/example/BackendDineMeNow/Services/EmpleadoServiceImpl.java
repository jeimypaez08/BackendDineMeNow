package com.example.BackendDineMeNow.Services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.EmpleadoDto;
import com.example.BackendDineMeNow.Dtos.EmpleadoRegistroDto;
import com.example.BackendDineMeNow.Mapper.EmpleadoMapper;
import com.example.BackendDineMeNow.models.Empleado;
import com.example.BackendDineMeNow.models.EmpleadoAuth;
import com.example.BackendDineMeNow.repositories.EmpleadoAuthRepository;
import com.example.BackendDineMeNow.repositories.EmpleadoRepository;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    private final EmpleadoRepository empleadoRepo;//Inyección de dependencias
    private final EmpleadoMapper empleadoMapper;//Inyección de dependencias
    private final EmpleadoAuthRepository empleadoAuthRepo;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepo, //Inyección de dependencias
                               EmpleadoMapper empleadoMapper,
                               EmpleadoAuthRepository empleadoAuthRepo,
                               PasswordEncoder passwordEncoder) {
        this.empleadoRepo = empleadoRepo;//Inyección de dependencias
        this.empleadoMapper = empleadoMapper; 
        this.empleadoAuthRepo = empleadoAuthRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public EmpleadoRegistroDto registrarEmpleado(EmpleadoRegistroDto dto) {
        //validar duplicados
        if(empleadoRepo.existsByCorreo(dto.getCorreo())){//Validar si el correo ya está registrado
            throw new RuntimeException("El correo ya está registrado");
        }
        if(empleadoRepo.existsByDocumentoNumero(dto.getDocumento().getNumero())){//Validar si el número de documento ya está registrado
            throw new RuntimeException("El número de documento ya está registrado");
        }
        if(empleadoAuthRepo.existsByUser(dto.getUser())){//Validar si el nombre de usuario ya está registrado
            throw new RuntimeException("El nombre de usuario ya está registrado");
        }

        //Guardar perfil
        Empleado empleado = Empleado.builder()//Construcción del objeto Empleado a partir del DTO
            .documento(dto.getDocumento())// Asignar el objeto Documento directamente
            .nombre(dto.getNombre())// Asignar el nombre del empleado
            .apellido(dto.getApellido())
            .telefono(dto.getTelefono())
            .correo(dto.getCorreo())
            .eps(dto.getEps())
            .arl(dto.getArl())
            .direccion(dto.getDireccion())
            .rol(dto.getRol())
            .estado("DISPONIBLE") // Estado inicial por defecto
            .foto(dto.getFoto())
            .idRestaurante(dto.getIdRestaurante())
            .build();

        Empleado empleadoGuardado = empleadoRepo.save(empleado);// Guardar el empleado en la base de datos

        //Guardar Atuenticación
        EmpleadoAuth auth = new EmpleadoAuth();// Construcción del objeto EmpleadoAuth para la autenticación
            auth.setId(empleadoGuardado.getId());// Asignar el mismo ID del empleado para la autenticación
            auth.setUser(dto.getUser());// Asignar el nombre de usuario
            auth.setPass(passwordEncoder.encode(dto.getPassword()));// Encriptar la contraseña antes de guardarla
            auth.setRoles(List.of(dto.getRol()));//rol asignado por el restaurante

            empleadoAuthRepo.save(auth);// Guardar la información de autenticación en la base de datos
            return dto;// Devolver el DTO de registro del empleado
    }

    @Override
    public List<EmpleadoDto> listarEmpleados() {// Listar todos los empleados
        return empleadoMapper.toEmpleadoDtoList(empleadoRepo.findAll());// Obtener todos los empleados de la base de datos y convertirlos a una lista de DTOs utilizando el mapper
    }


    @Override
    public List<EmpleadoDto> listarPorRestaurante(String idRestaurante) {// Listar empleados por ID de restaurante
        return empleadoMapper.toEmpleadoDtoList(empleadoRepo.findByIdRestaurante(idRestaurante));// Obtener los empleados que pertenecen a un restaurante específico utilizando el método personalizado del repositorio y convertirlos a una lista de DTOs utilizando el mapper
    }


    //obtener empleados por id
    @Override
    public EmpleadoDto obtenerPorId(String id) {
        Empleado empleado = empleadoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Id de empleado no encontrado, id: " + id));
        return empleadoMapper.toEmpleadoDto(empleado);
    }

    //obtener empleados por numero de documento
    @Override
    public EmpleadoDto obtenerPorDocumento(String numero){
        Empleado empleado = empleadoRepo.findByDocumentoNumero(numero)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con documento: " + numero));
        return empleadoMapper.toEmpleadoDto(empleado);
    }

    //actualizar empleados por id
    @Override
    public EmpleadoDto actualizarEmpleado(String id, EmpleadoDto dto) {
        Empleado empleado = empleadoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Id de empleado no encontrado, id: " + id));
            empleadoMapper.actualizarEmpleado(dto, empleado);// Actualizar los campos del empleado con los valores del DTO utilizando el método de actualización del mapper
        
        return empleadoMapper.toEmpleadoDto(empleadoRepo.save(empleado)); // Guardar el empleado actualizado y convertirlo a DTO para devolverlo
    }

    //actualizar empleados por numero de documento
    @Override
    public EmpleadoDto actualizarPorDocumento(String numero, EmpleadoDto dto) {
        Empleado empleado = empleadoRepo.findByDocumentoNumero(numero)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con documento: " + numero));
        empleadoMapper.actualizarEmpleado(dto, empleado);
        return empleadoMapper.toEmpleadoDto(empleadoRepo.save(empleado));
    }

    @Override
    public void eliminarEmpleado(String id) {// Eliminar un empleado por su ID
        if (!empleadoRepo.existsById(id)) {// Verificar si el empleado existe antes de intentar eliminarlo
            throw new RuntimeException("Empleado no encontrado, id: " + id);
        }
        empleadoRepo.deleteById(id);// Eliminar el empleado de la base de datos
        empleadoAuthRepo.deleteById(id);// Eliminar también la autenticación asociada al empleado
    }

    // Eliminar un empleado por su número de documento
    @Override
    public void eliminarPorDocumento(String numero) {
        Empleado empleado = empleadoRepo.findByDocumentoNumero(numero)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con documento: " + numero));
        empleadoRepo.deleteById(empleado.getId());// Eliminar el empleado de la base de datos
        empleadoAuthRepo.deleteById(empleado.getId());// Eliminar también la autenticación asociada al empleado
    }

}
