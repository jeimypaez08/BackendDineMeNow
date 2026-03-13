package com.example.BackendDineMeNow.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.BackendDineMeNow.Dtos.UsuarioDto;
import com.example.BackendDineMeNow.Services.UsuarioService;

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
@RequestMapping("/UserController")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Crear un usuario
    @PostMapping("/crear")
    public ResponseEntity<UsuarioDto> createUser(@RequestBody UsuarioDto usuarioDto) {
        UsuarioDto creando = usuarioService.crearUser(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creando);
    }

    //Lista usuarios
    @GetMapping("/List")
    public ResponseEntity<List<UsuarioDto>> obtenerUsers() {
        return ResponseEntity.ok(usuarioService.ListaUsers());
    }

    //Actualiza usuario
    @PutMapping("/Update/{id}")
    public ResponseEntity<UsuarioDto> actualizar(@PathVariable String id, @RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.ok(usuarioService.actUser(id, usuarioDto));
    }

    //Borrar usuario
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        usuarioService.borrarUser(id);
        return ResponseEntity.noContent().build();
    }
    
    

}
