package com.example.BackendDineMeNow.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.EmpleadoAuth;

public interface EmpleadoAuthRepository extends MongoRepository<EmpleadoAuth, String> {
    boolean existsByUser(String user); // Método para verificar si un usuario con el mismo nombre de usuario ya existe
    Optional<EmpleadoAuth> findByUser(String user); // Método para encontrar un empleado por nombre de usuario

}
