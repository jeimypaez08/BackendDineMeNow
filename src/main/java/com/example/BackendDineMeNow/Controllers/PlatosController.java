package com.example.BackendDineMeNow.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendDineMeNow.Dtos.PlatosDto;
import com.example.BackendDineMeNow.Services.PlatosService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/PlatosController")
public class PlatosController {

    private final PlatosService platosService;

    public PlatosController(PlatosService platosService) {
        this.platosService = platosService;
    }

    // Crear plato
    @PostMapping("/crearPlato")
    public ResponseEntity<PlatosDto> crearPlato(@RequestBody PlatosDto platosDto) {
        PlatosDto creando = platosService.crearPlatos(platosDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creando);
    }

    // Listar platos
    @GetMapping("/listarPlatos")
    public ResponseEntity<List<PlatosDto>> obtenerPlatos(@PathVariable String id,@RequestBody PlatosDto platosDto) {
        return ResponseEntity.ok(platosService.ListaPlatos());
    }
    
    @DeleteMapping("/borrarPlato/{id}")
    public ResponseEntity<Void> borrarPlato(@PathVariable String id) {
        platosService.borrarPlatos(id);
        return ResponseEntity.noContent().build();
    }

}
