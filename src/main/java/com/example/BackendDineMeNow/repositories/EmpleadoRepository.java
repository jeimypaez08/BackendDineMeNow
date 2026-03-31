package com.example.BackendDineMeNow.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.Empleado;

public interface EmpleadoRepository extends MongoRepository<Empleado, String> {

    boolean existsByDocumentoNumero(String numeroDocumento);// Método para verificar si un empleado con el mismo número de documento ya existe
    boolean existsByCorreo(String correo);// Método para verificar si un empleado con el mismo correo electrónico ya existe

    Optional<Empleado> findByCorreo(String correo);// Método para encontrar un empleado por correo electrónico
    Optional<Empleado> findByDocumentoNumero(String numeroDocumento);// Método para encontrar un empleado por número de documento

    List<Empleado> findByIdRestaurante(String idRestaurante); // Método para encontrar empleados por ID de restaurante
    List<Empleado> findByEstado(String estado); // Método para encontrar empleados por estado

}
