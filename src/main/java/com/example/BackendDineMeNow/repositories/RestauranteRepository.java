package com.example.BackendDineMeNow.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.EstadoRestaurante;
import com.example.BackendDineMeNow.models.Restaurante;

public interface RestauranteRepository extends MongoRepository<Restaurante, String>{

    boolean existsByNit(String nit);// Verificar si un restaurante con el mismo nit ya existe
    boolean existsByCorreo(String correo);// Verificar si un restaurante con el mismo correo ya existe

    Optional<Restaurante> findByNit(String nit);// Buscar un restaurante por su nit
    Optional<Restaurante> findByCorreo(String correo);// Buscar un restaurante por su correo

    long countByEstado(EstadoRestaurante estado);


    List<Restaurante> findByEstado(EstadoRestaurante estado);// Buscar restaurantes por su estado (activo, inactivo, pendiente etc.)
    List<Restaurante> findByCategoria(String categoria);// Buscar restaurantes por su categoría (italiano, chino, mexicano etc.)


}
