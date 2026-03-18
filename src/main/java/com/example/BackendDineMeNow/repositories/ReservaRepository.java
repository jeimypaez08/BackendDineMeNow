package com.example.BackendDineMeNow.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.Reserva;

public interface ReservaRepository extends MongoRepository<Reserva, String> {

}
