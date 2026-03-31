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
import com.example.BackendDineMeNow.Dtos.EmpleadoRegistroDto;
import com.example.BackendDineMeNow.Services.EmpleadoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;// Inyección de dependencia del servicio de empleados a través del constructor

    public EmpleadoController(EmpleadoService empleadoService) {// Constructor para inyectar la dependencia del servicio de empleados
        this.empleadoService = empleadoService;// Asignar la instancia del servicio de empleados al campo de la clase para su uso en los métodos del controlador        
    }

    //POST /api/empleados/registro
    @PostMapping("/registro")
    public ResponseEntity<EmpleadoRegistroDto> registar(@RequestBody EmpleadoRegistroDto dto) {
         return ResponseEntity.status(HttpStatus.CREATED)
             .body(empleadoService.registrarEmpleado(dto));
    }

    //GET /api/empleados
    @GetMapping
    public ResponseEntity<List<EmpleadoDto>> listar() {
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    //GET /api/empleados/restaurante/{id_Restaurante}
    @GetMapping("/restaurante/{idRestaurante}")
        public ResponseEntity<List<EmpleadoDto>> listarPorRestaurante(@PathVariable String idRestaurante){
            return ResponseEntity.ok(empleadoService.listarPorRestaurante(idRestaurante));
        }
    
    //GET /api/empleados/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDto> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(empleadoService.obtenerPorId(id));
    }

    //GET /api/empleados/documento/{numero}
    @GetMapping("/documento/{numero}")
    public ResponseEntity<EmpleadoDto> obtenerPorDocumento(@PathVariable String numero) {
        return ResponseEntity.ok(empleadoService.obtenerPorDocumento(numero));
    }


    //PUT /api/empleados/Update/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDto> actualizarEmpleado(@PathVariable String id, @RequestBody EmpleadoDto dto) {
        return ResponseEntity.ok(empleadoService.actualizarEmpleado(id, dto));
    }
    
     //PUT /api/empleados/Update/documento/{numero}
    @PutMapping("/documento/{numero}")
    public ResponseEntity<EmpleadoDto> actualizarporDocumento(@PathVariable String numero, @RequestBody EmpleadoDto dto) {
        return ResponseEntity.ok(empleadoService.actualizarPorDocumento(numero, dto));
    }

    //DELETE /api/empleados/Delete/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable String id) {
        empleadoService.eliminarEmpleado(id);
        return ResponseEntity.noContent().build();
    }

    //DELETE /api/empleados/Delete/documento/{numero}
    @DeleteMapping("/documento/{numero}")
    public ResponseEntity<Void> eliminarPorDocumento(@PathVariable String numero) {
        empleadoService.eliminarPorDocumento(numero);
        return ResponseEntity.noContent().build();
    }

}
