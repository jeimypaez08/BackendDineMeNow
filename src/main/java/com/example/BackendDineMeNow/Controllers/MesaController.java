package com.example.BackendDineMeNow.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendDineMeNow.Dtos.MesaDto;
import com.example.BackendDineMeNow.Services.MesaService;


@RestController
@RequestMapping("/MesasController")
public class MesaController {
    private final MesaService mesaService;
    
    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    // Crear mesa

    @PostMapping("/crearMesa")
    public ResponseEntity<MesaDto> createMesa(@RequestBody MesaDto mesaDto){
        MesaDto creando = mesaService.crearMesa(mesaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creando);
    }
    
    // Listar mesas

    @GetMapping("/listarMesas")
    public ResponseEntity<List<MesaDto>> obtenerMesa() {
        return ResponseEntity.ok(mesaService.ListaMesas());
    }

    // Actualizar mesa
    @PutMapping("/actualizarMesa/{id}")
    public ResponseEntity<MesaDto> actualizarMesa(@PathVariable String id, @RequestBody MesaDto mesaDto) {
        return ResponseEntity.ok(mesaService.actMesa(id, mesaDto));
    }

    // Borrar mesa
    @DeleteMapping("/borrarMesa/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        mesaService.borrarMesa(id);
        return ResponseEntity.noContent().build();
    }

}
