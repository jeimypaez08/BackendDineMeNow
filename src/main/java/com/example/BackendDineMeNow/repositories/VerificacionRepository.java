package com.example.BackendDineMeNow.repositories;


import com.example.BackendDineMeNow.models.VerificacionRegistro;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface VerificacionRepository extends MongoRepository<VerificacionRegistro, String> {
    
    // Para buscar el código que el usuario escribió en el FrontEnd
    Optional<VerificacionRegistro> findByCorreoAndCodigo(String correo, String codigo);
    
    // Para borrar códigos viejos si el usuario pide uno nuevo
    void deleteByCorreo(String correo);
}