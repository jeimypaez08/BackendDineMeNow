package com.example.BackendDineMeNow.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "recuperacionesPassword")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecuperacionPassword {

    @Id
    private String id;

    private String correo;
    private String codigo;

    @Indexed(expireAfterSeconds = 300)
    private Date fechaCreacion;
}
