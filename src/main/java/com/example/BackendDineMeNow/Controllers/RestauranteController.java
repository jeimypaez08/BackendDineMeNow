package com.example.BackendDineMeNow.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendDineMeNow.Dtos.RestauranteDto;
import com.example.BackendDineMeNow.Services.RestauranteService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;





@RestController
@RequestMapping ("/RestauranteController")
public class RestauranteController {
  private final RestauranteService restauranteService;

  public RestauranteController(RestauranteService restauranteService) {
    this.restauranteService = restauranteService;
  }
  //crear restaurante
  @PostMapping("/CrearRestaurante")
  public ResponseEntity<RestauranteDto> createRestaurante (@RequestBody RestauranteDto restauranteDto){
    RestauranteDto creando = restauranteService.crearRestaurante(restauranteDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(creando);
  }

  //Lista Restaurantes
  @GetMapping("/ListRestaurante")
  public ResponseEntity<List<RestauranteDto>> obtenerRestaurantes() {
      return ResponseEntity.ok(restauranteService.ListRestaurante());
  }
  
  //Actualizar Restaurante
  @PutMapping("/updateRestaurante/{id}")
  public ResponseEntity<RestauranteDto> actualizarRestaurante(@PathVariable String id, @RequestBody RestauranteDto restauranteDto){
    return ResponseEntity.ok(restauranteService.actualizarRestaurante(id, restauranteDto));
  }
  

  //Borrar Restaurante
  @DeleteMapping("/DeleteRestaurante/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable String id) {
    restauranteService.borrarRestaurante(id);
    return ResponseEntity.noContent().build();
  }
  
  }
  

