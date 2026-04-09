package com.example.BackendDineMeNow.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.LoginRequestDto;
import com.example.BackendDineMeNow.Dtos.LoginResponseDto;
import com.example.BackendDineMeNow.models.Cliente;
import com.example.BackendDineMeNow.models.ClienteAuth;
import com.example.BackendDineMeNow.repositories.ClienteAuthRepository;
import com.example.BackendDineMeNow.repositories.ClienteRepository;
import com.example.BackendDineMeNow.repositories.RestauranteRepository;
import com.example.BackendDineMeNow.models.Rol;

@Service
public class AuthServiceImpl implements AuthService {

    private final ClienteAuthRepository authRepo;
    private final ClienteRepository clienteRepo;
    private final RestauranteRepository restauranteRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(ClienteAuthRepository authRepo, 
                           ClienteRepository clienteRepo, 
                           RestauranteRepository restaurateRepo,
                           PasswordEncoder passwordEncoder) {
        this.authRepo = authRepo;
        this.clienteRepo = clienteRepo;
        this.restauranteRepo = restaurateRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto dto) {

        String identificador = dto.getIdentificador();

        try{
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
            .nombre(cliente.getNombreCliente())
            .apellido(cliente.getApellido())
            .correo(cliente.getCorreo())
            .roles(auth.getRoles())
            .build();

    } catch(RuntimeException e){

        //intentar como restaurante

        return restauranteRepo.findByCorreo(identificador)
            .or(()-> restauranteRepo.findByNit(identificador))
            .map(restaurante->{
                //verificar contraseña del restaurante
                if (!passwordEncoder.matches(dto.getPassword(), restaurante.getPassword())) {
                    throw new RuntimeException("Contraseña incorrecta");
                }
                //verificar que este activo
                if(restaurante.getEstado() != com.example.BackendDineMeNow.models.EstadoRestaurante.ACTIVO){
                    throw new RuntimeException("Restaurante pendiente por aprobacion");
                }
                //respuesta para el restaurante
                return LoginResponseDto.builder()
                .mensaje("Inicio de Sesion exitoso (Restaurante)")
                .id(restaurante.getId())
                .nombre(restaurante.getNombre())
                .correo(restaurante.getCorreo())
                .roles(List.of(Rol.ROL_RESTAURANTE))
                .build();
            })
            .orElseThrow(() -> new RuntimeException("Usuario o Restaurante no encontrado"));
    }
}}

