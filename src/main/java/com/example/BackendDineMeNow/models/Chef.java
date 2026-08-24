package com.example.BackendDineMeNow.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Chef")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chef {
@Id
private String id;
private String id_platos;
}
