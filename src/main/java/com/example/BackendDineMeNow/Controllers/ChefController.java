package com.example.BackendDineMeNow.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendDineMeNow.Dtos.ChefDto;
import com.example.BackendDineMeNow.Services.ChefService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/ChefController")
public class ChefController {
private final ChefService chefService;

public ChefController(ChefService chefService) {
    this.chefService = chefService;
}
//crear un chef
@PostMapping("/crear")
public ResponseEntity<ChefDto> crearChef(@RequestBody ChefDto chefDto) {
    ChefDto creado = chefService.crearChef(chefDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(creado);
}
//Listar Chefs
@PostMapping("/List")
public ResponseEntity<List<ChefDto>> obtenerChefs() {
    return ResponseEntity.ok(chefService.obtenerTodosLosChefs());
}
//Actualizar Chef
@PostMapping("/Update/{id}")
public ResponseEntity<ChefDto> actualizarChef(@PathVariable String id, @RequestBody ChefDto chefDto) {
    return ResponseEntity.ok(chefService.actualizarChef(id, chefDto));
}
//Eliminar Chef
@DeleteMapping("/Delete/{id}")
public ResponseEntity<Void> eliminarChef(@PathVariable String id) {
    chefService.eliminarChef(id);
    return ResponseEntity.noContent().build();
}
}
