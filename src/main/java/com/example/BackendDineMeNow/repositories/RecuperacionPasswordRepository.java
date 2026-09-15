package com.example.BackendDineMeNow.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.RecuperacionPassword;

public interface RecuperacionPasswordRepository extends MongoRepository<RecuperacionPassword, String> {

    Optional<RecuperacionPassword> findByCorreoAndCodigo(String correo, String codigo);

    void deleteByCorreo(String correo);
}
