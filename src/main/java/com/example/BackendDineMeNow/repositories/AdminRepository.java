package com.example.BackendDineMeNow.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.Admin;

import java.util.Optional;


public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findById(String id);
    Optional<Admin> findByCorreo(String correo);

}
