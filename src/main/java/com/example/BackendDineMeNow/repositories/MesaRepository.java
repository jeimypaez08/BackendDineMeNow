package com.example.BackendDineMeNow.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.BackendDineMeNow.models.Mesa;
import java.util.List;


@Repository
public interface MesaRepository extends MongoRepository<Mesa, String> {

    List<Mesa> findByNitRestaurante(String nitRestaurante);

    void deleteByNitRestauranteAndNumMesa(String nitRestaurante, String numMesa);
    void deleteById(String id);

}
