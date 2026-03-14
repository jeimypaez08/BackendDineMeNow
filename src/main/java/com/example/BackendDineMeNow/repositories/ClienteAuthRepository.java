package com.example.BackendDineMeNow.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.ClienteAuth;

public interface ClienteAuthRepository extends MongoRepository<ClienteAuth, String> {

}
