package com.example.BackendDineMeNow.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.Platos;

public interface PlatosRepository extends MongoRepository<Platos, String> {

}
