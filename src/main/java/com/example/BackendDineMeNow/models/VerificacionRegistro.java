package com.example.BackendDineMeNow.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "verificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificacionRegistro {
    @Id
    private String id;

    private String correo;
    
    private String codigo;

    // El índice TTL (Time To Live)
    // expireAfterSeconds = 300 significa que el registro se borrará 5 minutos 
    // después de la hora guardada en 'fechaCreacion'
    @SuppressWarnings("removal")
    @Indexed(expireAfterSeconds = 300)
    private Date fechaCreacion;
}
