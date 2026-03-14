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
@RequestMapping("/ClienteController")
public class ClienteController {
    private final ClienteService usuarioService;

    public ClienteController(ClienteService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Crear un usuario
    @PostMapping("/crear")
    public ResponseEntity<ClienteDto> createUser(@RequestBody ClienteDto usuarioDto) {
        ClienteDto creando = usuarioService.crearUser(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creando);
    }

    //Lista usuarios
    @GetMapping("/List")
    public ResponseEntity<List<ClienteDto>> obtenerUsers() {
        return ResponseEntity.ok(usuarioService.ListaUsers());
    }

    //Actualiza usuario
    @PutMapping("/Update/{id}")
    public ResponseEntity<ClienteDto> actualizar(@PathVariable String id, @RequestBody ClienteDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.actUser(id, usuarioDto));
    }

    //Borrar usuario
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        usuarioService.borrarUser(id);
        return ResponseEntity.noContent().build();
    }

    //Registrar con encryp ???
    @PostMapping("/Registrar")
    public ResponseEntity<ClienteRegistroDto> register(@RequestBody ClienteRegistroDto usuarioRegistroDto) {
        ClienteRegistroDto crear = usuarioService.registerUser(usuarioRegistroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(crear);
    }
    
    
    

}
