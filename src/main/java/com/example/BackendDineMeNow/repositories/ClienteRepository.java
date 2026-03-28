package com.example.BackendDineMeNow.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.Cliente;


public interface ClienteRepository extends MongoRepository<Cliente, String> {
    // Verificar si el correo ya existe
    boolean existsByCorreo(String correo); 
    Optional<Cliente> findByCorreo(String correo);// Buscar cliente por correo para autenticación

    // Verificar si el documento ya existe
    boolean existsByDocumentoNumero(String numero);
    Optional<Cliente> findByDocumentoNumero(String numero); // Buscar cliente por número de documento

}
