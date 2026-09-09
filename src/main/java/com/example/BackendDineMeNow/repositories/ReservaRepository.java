package com.example.BackendDineMeNow.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.BackendDineMeNow.models.Reserva;

public interface ReservaRepository extends MongoRepository<Reserva, String> {

    boolean existsByNitRestauranteAndNumMesaAndFechaAndHoraAndNombreRestaurante(String nitRestaurante, String numMesa, LocalDate fecha, LocalTime hora, String nombreRestaurante);

    //buscar reservas por nit del restaurante
    List<Reserva> findByNitRestaurante(String nitRestaurante);
    List<Reserva> findByNombreRestaurante(String nombreRestaurante);
    List<Reserva> findByNitRestauranteAndFecha(String nitRestaurante, LocalDate fecha);
    List<Reserva> findByNombreRestauranteAndFecha(String nombreRestaurante, LocalDate fecha);

    //buscar reserva por id de cliente
    List<Reserva> findByIdCliente(String idCliente);

}
