package com.example.BackendDineMeNow.Controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendDineMeNow.Dtos.CambiarPasswordRecuperacionDto;
import com.example.BackendDineMeNow.Dtos.LoginRequestDto;
import com.example.BackendDineMeNow.Dtos.LoginResponseDto;
import com.example.BackendDineMeNow.Dtos.ResetTokenResponseDto;
import com.example.BackendDineMeNow.Dtos.SolicitarRecuperacionDto;
import com.example.BackendDineMeNow.Dtos.VerificarCodigoRecuperacionDto;
import com.example.BackendDineMeNow.Services.AuthService;


@RestController
@RequestMapping("/api/auth") // Ruta base para el controlador de autenticación
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping("/recuperar-password/solicitar")
    public ResponseEntity<?> solicitarRecuperacion(@RequestBody SolicitarRecuperacionDto dto) {
        System.out.println(">>> ENTRO A SOLICITAR RECUPERACION");
        System.out.println(">>> CORREO: " + dto.getCorreo());
        authService.solicitarRecuperacion(dto);
        return ResponseEntity.ok(Map.of(
                "mensaje", "Si el correo está registrado, recibirás un código de recuperación."
        ));
    }

    @PostMapping("/recuperar-password/verificar-codigo")
    public ResponseEntity<ResetTokenResponseDto> verificarCodigoRecuperacion(
            
            @RequestBody VerificarCodigoRecuperacionDto dto) {
        return ResponseEntity.ok(authService.verificarCodigoRecuperacion(dto));
    }

    @PostMapping("/recuperar-password/cambiar")
    public ResponseEntity<?> cambiarPassword(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestBody CambiarPasswordRecuperacionDto dto) {

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("error", "Token de recuperación requerido"));
        }

        String resetToken = authorization.substring(7).trim();
        try {
            authService.cambiarPasswordConToken(resetToken, dto);
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Token de recuperación inválido o expirado"));
        }
    }
    

}
