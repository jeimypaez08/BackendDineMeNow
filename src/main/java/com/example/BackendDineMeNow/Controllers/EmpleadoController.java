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

import com.example.BackendDineMeNow.Dtos.EmpleadoDto;
import com.example.BackendDineMeNow.Services.EmpleadoService;

@RestController
@RequestMapping("/EmpleadoController")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<EmpleadoDto> createEmpleado(@RequestBody EmpleadoDto empleadoDto) {
        EmpleadoDto creando = empleadoService.crearEmpleado(empleadoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creando);
    }

    @GetMapping("/List")
    public ResponseEntity<List<EmpleadoDto>> obtenerEmpleados() {
        return ResponseEntity.ok(empleadoService.ListEmpleados());
    }

    @PutMapping("/Update/{id}")
    public ResponseEntity<EmpleadoDto> actualizarEmpleado(@PathVariable String id, @RequestBody EmpleadoDto empleadoDto) {
        return ResponseEntity.ok(empleadoService.actEmpleado(id, empleadoDto));
    }

    @DeleteMapping("Delete/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable String id) {
        empleadoService.borrarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

}
