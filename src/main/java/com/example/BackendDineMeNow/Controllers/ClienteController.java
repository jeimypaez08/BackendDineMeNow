package com.example.BackendDineMeNow.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.BackendDineMeNow.Dtos.ClienteDto;
import com.example.BackendDineMeNow.Dtos.ClienteRegistroDto;
import com.example.BackendDineMeNow.Services.ClienteService;

import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/api/clientes") //Ruta base para el controlador de clientes
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // POST api/clientes/registro
    @PostMapping("/registro")
    public ResponseEntity<ClienteRegistroDto> registrarCliente(@RequestBody ClienteRegistroDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
        .body(clienteService.registrarCliente(dto));
}

    // GET api/clientes
    @GetMapping
    public ResponseEntity<List<ClienteDto>> listar() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }


    // GET api/clientes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(clienteService.obtenerClientePorId(id));
    }

    @GetMapping("/documento/{numero}")
public ResponseEntity<ClienteDto> obtenerPorDocumento(@PathVariable String numero) {
    return ResponseEntity.ok(clienteService.obtenerClientePorDocumento(numero));
}

    //Actualiza usuario- PUT api/clientes/Update/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizar(@PathVariable String id, @RequestBody ClienteDto dto) {
        return ResponseEntity.ok(clienteService.actualizarCliente(id, dto));
    }

    

    //Borrar usuario- DELETE api/clientes/Delete/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

}
