package com.example.BackendDineMeNow.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.Empleado;

public interface EmpleadoRepository extends MongoRepository<Empleado, String> {

}
