package com.example.BackendDineMeNow.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.Reserva;

public interface ReservaRepository extends MongoRepository<Reserva, String> {

    boolean existsByNitRestauranteAndNumMesaAndFechaAndHora(String nitRestaurante, String numMesa, LocalDate fecha, LocalTime hora);

    //buscar reservas por nit del restaurante
    List<Reserva> findByNitRestaurante(String nitRestaurante);
    List<Reserva> findByNitRestauranteAndFecha(String nitRestaurante, LocalDate fecha);

}
