package com.example.BackendDineMeNow.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.ClienteAuth;

public interface ClienteAuthRepository extends MongoRepository<ClienteAuth, String> {

    boolean existsByUser(String user);// Verificar si el usuario ya existe, devuelve true o false
    Optional<ClienteAuth> findByUser(String user); // Buscar cliente por usuario para autenticación, Optional:contenedor que puede tener un valor o puede estar vacío

}
