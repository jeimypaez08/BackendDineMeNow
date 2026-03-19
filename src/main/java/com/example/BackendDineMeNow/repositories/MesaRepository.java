package com.example.BackendDineMeNow.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.BackendDineMeNow.models.Mesa;

@Repository
public interface MesaRepository extends MongoRepository<Mesa, String> {


}
