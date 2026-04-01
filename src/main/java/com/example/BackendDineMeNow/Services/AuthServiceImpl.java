package com.example.BackendDineMeNow.Services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.LoginRequestDto;
import com.example.BackendDineMeNow.Dtos.LoginResponseDto;
import com.example.BackendDineMeNow.models.Cliente;
import com.example.BackendDineMeNow.models.ClienteAuth;
import com.example.BackendDineMeNow.repositories.ClienteAuthRepository;
import com.example.BackendDineMeNow.repositories.ClienteRepository;

@Service
public class AuthServiceImpl implements AuthService {

    private final ClienteAuthRepository authRepo;
    private final ClienteRepository clienteRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(ClienteAuthRepository authRepo, 
                           ClienteRepository clienteRepo, 
                           PasswordEncoder passwordEncoder) {
        this.authRepo = authRepo;
        this.clienteRepo = clienteRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {

        String identificador = dto.getIdentificador();
        ClienteAuth auth;


        //1. Buscar el cliente por usuario
        Optional<ClienteAuth> porUser = authRepo.findByUser(identificador);
        
        if (porUser.isPresent()){
            auth = porUser.get();
        } else {
            //2. Si no se encuentra por usuario, buscar por correo o numero de documento
            Optional<Cliente> porCorreo = clienteRepo.findByCorreo(identificador);
            Optional<Cliente> porDocumento = clienteRepo.findByDocumentoNumero(identificador);

            Cliente cliente = porCorreo
                .or(()->porDocumento)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));

            auth = authRepo.findById(cliente.getId())
                .orElseThrow(()-> new RuntimeException("Credenciales no encontradas para el cliente"));
        }

        //3. Verificar la contraseña
        if (!passwordEncoder.matches(dto.getPassword(), auth.getPass())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        //4. Buscar perfil
        Cliente cliente = clienteRepo.findById(auth.getId())
            .orElseThrow(()-> new RuntimeException("Perfil de cliente no encontrado"));

        //5. Construir respuesta
        return LoginResponseDto.builder()
            .mensaje("Login exitoso")
            .id(cliente.getId())
            .nombre(cliente.getNombre())
            .correo(cliente.getCorreo())
            .roles(auth.getRoles())
            .build();


}
}
