package com.example.BackendDineMeNow.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Documento {
    private String id;
    private String tipo_doc;
}
