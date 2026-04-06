package com.example.BackendDineMeNow.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendDineMeNow.Dtos.PlatosDto;
import com.example.BackendDineMeNow.Services.PlatosService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/platos")
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
    public ResponseEntity<List<PlatosDto>> obtenerPlatos() {
        return ResponseEntity.ok(platosService.listarPlatos());
    }

    // Listar platos por restaurante
    @GetMapping("/listarPlatos/{nitRestaurante}")
    public ResponseEntity<List<PlatosDto>> obtenerPlatosPorRestaurante(@PathVariable String nitRestaurante) {
        return ResponseEntity.ok(platosService.listarPorRestaurante(nitRestaurante));
    }

    // Buscar platos por nombre: /api/platos/buscar?nomPlato=Ajiaco
    //El signo ? separa la dirección de la página de los filtros.
    //flexibilidad: en el futuro buscamos por nombre Y por precio máximo. Con query params es fácil:
    //buscar?nombre=Bandeja&precioMax=30000
    @GetMapping("/buscar")
    public ResponseEntity<List<PlatosDto>> buscarPlatos(@RequestParam String nomPlato) {
        return ResponseEntity.ok(platosService.listarPorNombre(nomPlato));
    }

    //buscar platos por nombre y precio máximo: /api/platos/buscar?nomPlato=Ajiaco&precioMax=30000
    @GetMapping("/buscar/filtros")
    public ResponseEntity<List<PlatosDto>> buscarConFiltros(//Recibimos ambos filtros como query params:
            @RequestParam String nomPlato,//filtro por nombre
            //defaultValue hace que si no envían precioMax, use 1 millón por defecto
            @RequestParam (defaultValue = "1000000") double precioMax) {//filtro por precio máximo
        return ResponseEntity.ok(platosService.listarPorNombreYPrecioMax(nomPlato, precioMax));//Llamamos al método del servicio que hace la consulta con ambos filtros.
    }


    //actualizar plato por id
    @PutMapping("/actualizarPlato/{id}")
    public ResponseEntity<PlatosDto> actualizarPlato(@PathVariable String id, @RequestBody PlatosDto platosDto) {
        PlatosDto actualizado = platosService.actPlatos(id, platosDto);
        return ResponseEntity.ok(actualizado);
    }


    // Actualizar plato por nombre y nit restaurante
@PutMapping("/actualizarPlato/{nitRestaurante}/{nomPlato}")
public ResponseEntity<PlatosDto> actualizarPlato(
        @PathVariable String nitRestaurante, 
        @PathVariable String nomPlato, 
        @RequestBody PlatosDto platosDto) {
    PlatosDto actualizado = platosService.actualizarPlatosPorNitRestauranteAndNombre(nitRestaurante, nomPlato, platosDto);
    return ResponseEntity.ok(actualizado);
}

    @DeleteMapping("/borrarPlato/{id}")
    public ResponseEntity<Void> borrarPlato(@PathVariable String id) {
        platosService.borrarPlatos(id);
        return ResponseEntity.noContent().build();
    }

    // Borrar plato por restaurante y nombre
@DeleteMapping("/borrarPlato/{nitRestaurante}/{nomPlato}") 
public ResponseEntity<Void> eliminarPorNitAndnombre(
        @PathVariable String nitRestaurante, 
        @PathVariable String nomPlato){
    platosService.borrarPlatosPorNitRestauranteAndNombre(nitRestaurante, nomPlato);
    return ResponseEntity.noContent().build();
}
    }

