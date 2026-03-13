package com.example.BackendDineMeNow.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {

}
