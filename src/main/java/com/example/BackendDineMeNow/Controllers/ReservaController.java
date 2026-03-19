package com.example.BackendDineMeNow.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendDineMeNow.Dtos.ReservaDto;
import com.example.BackendDineMeNow.Services.ReservaService;

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
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Crear reserva
    @PostMapping("/CrearReservas")
    public ResponseEntity<ReservaDto> createReserva(@RequestBody ReservaDto reservaDto) {
        ReservaDto creando = reservaService.crearReserva(reservaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creando);
    }

    // Listar reservas
    @GetMapping("/ListReservas")
    public ResponseEntity<List<ReservaDto>> obtenerReserva() {
        return ResponseEntity.ok(reservaService.ListaReservas());
    }

    // Actualizar reserva
    @PutMapping("/UpdateReserva/{id}")
    public ResponseEntity<ReservaDto> actualizarReserva(@PathVariable String id, @RequestBody ReservaDto reservaDto) {
        return ResponseEntity.ok(reservaService.actReserva(id, reservaDto));
    }

    // Borrar reserva
    @DeleteMapping("/DeleteReserva/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        reservaService.borrarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
