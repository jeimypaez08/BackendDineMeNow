package com.example.BackendDineMeNow.Controllers;

import com.example.BackendDineMeNow.models.Cliente;
import com.example.BackendDineMeNow.models.VerificacionRegistro;
import com.example.BackendDineMeNow.repositories.ClienteRepository;
import com.example.BackendDineMeNow.repositories.VerificacionRepository;
import com.example.BackendDineMeNow.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/verificacion")
@CrossOrigin(origins = "*")
public class VerififacionRegistroController {
    @Autowired
    private VerificacionRepository verificacionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public ResponseEntity<?> enviarCodigoVerificacion(@RequestBody Map<String, String> payload) {
        String correo = payload.get("correo");
        if (correo == null || correo.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El correo es obligatorio"));
        }

        Optional<Cliente> clienteOptional = clienteRepository.findByCorreo(correo);
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }

        Cliente cliente = clienteOptional.get();
        verificacionRepository.deleteByCorreo(correo);

        String codigo = generarCodigoVerificacion(6);
        VerificacionRegistro verificacion = VerificacionRegistro.builder()
                .correo(correo)
                .codigo(codigo)
                .fechaCreacion(new Date())
                .build();

        verificacionRepository.save(verificacion);
        emailService.enviarCodigoVerificacion(correo, cliente.getNombreCliente(), codigo);

        return ResponseEntity.ok(Map.of("message", "Código de verificación enviado al correo"));
    }

    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarCodigo(@RequestBody Map<String, String> payload) {
        String correo = payload.get("correo");
        String codigo = payload.get("codigo");

        // 1. Buscar si existe el código para ese correo en la colección "verificaciones"
        Optional<VerificacionRegistro> dataToken = verificacionRepository.findByCorreoAndCodigo(correo, codigo);

        if (dataToken.isPresent()) {
            // 2. Si existe, buscamos al cliente en la colección "clientes"
            Optional<Cliente> clienteOptional = clienteRepository.findByCorreo(correo);
            if (clienteOptional.isPresent()) {
                Cliente cliente = clienteOptional.get();
                // 3. Cambiamos su estado a verificado
                cliente.setVerificado(true);
                clienteRepository.save(cliente);

                // 4. Borramos el código para que no se use dos veces (seguridad)
                verificacionRepository.delete(dataToken.get());

                return ResponseEntity.ok(Map.of("message", "¡Cuenta activada con éxito!"));
            } else {
                return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
            }
        }

        // 5. Si no existe o expiró (el TTL de MongoDB lo borró)
        return ResponseEntity.status(400).body(Map.of("error", "Código incorrecto o ha expirado"));
    }

    private String generarCodigoVerificacion(int length) {
        int minimo = (int) Math.pow(10, length - 1);
        int maximo = (int) Math.pow(10, length) - 1;
        return String.valueOf(ThreadLocalRandom.current().nextInt(minimo, maximo + 1));
    }
}
