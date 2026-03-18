package com.example.BackendDineMeNow.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.Restaurante;

public interface RestauranteRepository extends MongoRepository<Restaurante, String>{

}
