package com.example.BackendDineMeNow.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.Categorias;
import com.example.BackendDineMeNow.models.Platos;

public interface PlatosRepository extends MongoRepository<Platos, String> {

    //buscar plato que contenga el nombre (ignorando mayúsculas y minúsculas)
    List<Platos> findByNomPlatoContainingIgnoreCase(String nomPlato);
    //buscar plato por categoria
    List<Platos> findByCategIn(List<Categorias> categ);

    //buscar por nombre y que el precio sea menor o igual al valor enviado
    List<Platos> findByNomPlatoContainingIgnoreCaseAndPrecioLessThanEqual(String nomPlato, double precioMax);

    List<Platos> findByNitRestaurante(String nitRestaurante); //Buscar por nit del restaurante
    Optional<Platos> findByNitRestauranteAndNomPlato(String nitRestaurante, String nomPlato);//Buscar por nit del restaurante y nombre del plato

    void deleteByNitRestauranteAndNomPlato(String nitRestaurante, String nomPlato);//Eliminar por nit del restaurante y nombre del plato
    void deleteById(String id);//Eliminar por id del plato

}
