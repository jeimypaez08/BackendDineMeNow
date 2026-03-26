/*package com.example.BackendDineMeNow.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioAuthRepository aur;
    private final JwtService jwt;

    private AuthController(UsuarioAuthRepository aur, JwtService jwt){
        this.aur = aur;
        this.jwt = jwt;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UsuarioAuthDto dto){
       

        //Autenticar usuario
        Authentication auth = authManager.authenticate(new AbstractAuthenticationToken(dto.getUsername(), dto.getPassword()));
    }

    //consulta de usuario en base de datos
    UsuarioAuth usuario = aur.findByUser(dto.getUsuario()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    List<String> roles = usuario.getRoles().stream().map(Enum :: name).toList();

    //generar token
    String token = jwt.generarToken(dto.getUsuario(),roles);

    //respuesta con token
    Map<String, Object> respuesta = Map.of(
        k1: "token", token,
        k2: "user", dto.getUsuario(),
        k3: "roles", roles
        k4: "timestamp", localDateTime.now(),
        k5: "mensaje", vs:"Usuario autenticado correctamente"
    );
    return ResponseEntity.ok(respuesta);
}*/