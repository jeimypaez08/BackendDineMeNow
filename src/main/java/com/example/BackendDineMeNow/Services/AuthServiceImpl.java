package com.example.BackendDineMeNow.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.security.SecureRandom;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.LoginRequestDto;
import com.example.BackendDineMeNow.Dtos.LoginResponseDto;
import com.example.BackendDineMeNow.Dtos.CambiarPasswordRecuperacionDto;
import com.example.BackendDineMeNow.Dtos.ResetTokenResponseDto;
import com.example.BackendDineMeNow.Dtos.SolicitarRecuperacionDto;
import com.example.BackendDineMeNow.Dtos.VerificarCodigoRecuperacionDto;
import com.example.BackendDineMeNow.models.Cliente;
import com.example.BackendDineMeNow.models.ClienteAuth;
import com.example.BackendDineMeNow.models.EmpleadoAuth;
import com.example.BackendDineMeNow.repositories.AdminRepository;
import com.example.BackendDineMeNow.repositories.ClienteAuthRepository;
import com.example.BackendDineMeNow.repositories.ClienteRepository;
import com.example.BackendDineMeNow.repositories.EmpleadoAuthRepository;
import com.example.BackendDineMeNow.repositories.EmpleadoRepository;
import com.example.BackendDineMeNow.repositories.RestauranteRepository;
import com.example.BackendDineMeNow.repositories.RecuperacionPasswordRepository;
import com.example.BackendDineMeNow.models.RecuperacionPassword;
import com.example.BackendDineMeNow.models.Restaurante;
import com.example.BackendDineMeNow.security.JwtService;
import com.example.BackendDineMeNow.models.Rol;

@Service
public class AuthServiceImpl implements AuthService {

    private final ClienteAuthRepository authRepo;
    private final ClienteRepository clienteRepo;
    private final RestauranteRepository restauranteRepo;
    private final AdminRepository adminRepo;
    private final EmpleadoAuthRepository empleadoAuthRepo;
    private final EmpleadoRepository empleadoRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; //
    private final RecuperacionPasswordRepository recuperacionRepo;
    private final EmailService emailService;

    public AuthServiceImpl(ClienteAuthRepository authRepo, 
                           ClienteRepository clienteRepo, 
                           RestauranteRepository restaurateRepo,
                           AdminRepository adminRepo,
                           EmpleadoAuthRepository empleadoAuthRepo,
                           EmpleadoRepository empleadoRepo,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           RecuperacionPasswordRepository recuperacionRepo,
                           EmailService emailService) {
        this.authRepo = authRepo;
        this.clienteRepo = clienteRepo;
        this.restauranteRepo = restaurateRepo;
        this.adminRepo = adminRepo;
        this.empleadoAuthRepo = empleadoAuthRepo;
        this.empleadoRepo = empleadoRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.recuperacionRepo = recuperacionRepo;
        this.emailService = emailService;
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
        String token = jwtService.generarToken(cliente.getCorreo(), auth.getRoles());
        return LoginResponseDto.builder()
            .mensaje("Login exitoso")
            .token(token)
            .id(cliente.getId())
            .nombre(cliente.getNombreCliente())
            .apellido(cliente.getApellido())
            .correo(cliente.getCorreo())
            .roles(auth.getRoles())
            .build();

    } catch(RuntimeException e){

        //intentar como restaurante

        Optional<LoginResponseDto> restauranteRes = restauranteRepo.findByCorreo(identificador)
            .or(() -> restauranteRepo.findByNit(identificador))
            .map(restaurante->{
                //verificar contraseña del restaurante
                if (!passwordEncoder.matches(dto.getPassword(), restaurante.getPassword())) {
                    throw new RuntimeException("Contraseña incorrecta");
                }
                //verificar que este activo
                if(restaurante.getEstado() != com.example.BackendDineMeNow.models.EstadoRestaurante.ACTIVO){
                    throw new RuntimeException("Restaurante pendiente por aprobacion");
                }

                String token = jwtService.generarToken(restaurante.getCorreo(), List.of(Rol.ROL_RESTAURANTE));
                //respuesta para el restaurante
                return LoginResponseDto.builder()
                .mensaje("Inicio de Sesion exitoso (Restaurante)")
                .token(token)
                .id(restaurante.getId())
                .nombre(restaurante.getNombre())
                .correo(restaurante.getCorreo())
                .roles(List.of(Rol.ROL_RESTAURANTE))
                .mustChangePassword(restaurante.getMustChangePassword())
                .build();
            });
           if (restauranteRes.isPresent()) {
            return restauranteRes.get();
        }

        // Intentar como Empleado (Únicamente por correo en Empleado, recuperando pass de EmpleadoAuth)
            Optional<LoginResponseDto> empleadoRes = empleadoRepo.findByCorreo(identificador)
                .map(empleado -> {
                    EmpleadoAuth empleadoAuth = empleadoAuthRepo.findById(empleado.getId())
                        .orElseThrow(() -> new RuntimeException("Credenciales no encontradas para el empleado"));

                    if (!passwordEncoder.matches(dto.getPassword(), empleadoAuth.getPass())) {
                        throw new RuntimeException("Contraseña incorrecta");
                    }

                    List<Rol> roles = List.of(empleado.getRol());
                    String token = jwtService.generarToken(empleado.getCorreo(), roles);

                    return LoginResponseDto.builder()
                        .mensaje("Inicio de sesion exitoso (" + empleado.getRol() + ")")
                        .token(token)
                        .id(empleado.getId())
                        .nombre(empleado.getNombre())
                        .apellido(empleado.getApellido())
                        .correo(empleado.getCorreo())
                        .roles(roles)
                        .build();
                });

            if (empleadoRes.isPresent()) {
                return empleadoRes.get();
            }

            //intentar como admin
            return adminRepo.findByCorreo(identificador)
            .map(admin -> {
                if(!passwordEncoder.matches(dto.getPassword(), admin.getPassword())){
                    throw new RuntimeException("Contraseña incorrecta");
                }
                String token = jwtService.generarToken(admin.getCorreo(), List.of(Rol.ROL_ADMIN));
                return LoginResponseDto.builder()
                .mensaje("Inicio de sesion exitoso")
                .token(token)
                .id(admin.getId())
                .nombre("Administrador")
                .correo(admin.getCorreo())
                //convertir el  set<String> de roles a List<Rol> para que coincida con el dto
                .roles(List.of(Rol.ROL_ADMIN))
                .build();
            })
            .orElseThrow(() -> new RuntimeException("Credenciales no encontradas"));}}

    
    @Override
    public void solicitarRecuperacion(SolicitarRecuperacionDto dto) {
    String correo = dto.getCorreo() == null ? "" : dto.getCorreo().trim().toLowerCase();

    System.out.println(">>> ENTRO A SOLICITAR RECUPERACION");
    System.out.println(">>> CORREO A BUSCAR: " + correo);

    if (correo.isBlank()) {
        throw new IllegalArgumentException("El correo es obligatorio");
    }

    String nombreUsuario = "";
    boolean usuarioExiste = false;

    // 1. Intentar buscar primero en el repositorio de Cliente
    Optional<Cliente> clienteOptional = clienteRepo.findByCorreo(correo);
    if (clienteOptional.isPresent()) {
        usuarioExiste = true;
        nombreUsuario = clienteOptional.get().getNombreCliente();
        System.out.println(">>> ENCONTRADO EN REPOSITORIO CLIENTE: " + nombreUsuario);
    } else {
        // 2. Si no es un cliente, buscar en el repositorio de Restaurante
        System.out.println(">>> NO ES CLIENTE. BUSCANDO EN REPOSITORIO DE RESTAURANTE...");
        Optional<Restaurante> restauranteOptional = restauranteRepo.findByCorreo(correo);
        if (restauranteOptional.isPresent()) {
            usuarioExiste = true;
            nombreUsuario = restauranteOptional.get().getNombre();
            System.out.println(">>> ENCONTRADO EN REPOSITORIO RESTAURANTE: " + nombreUsuario);
        }
    }

    if (!usuarioExiste) {
        System.out.println(">>> USUARIO NO EXISTE EN NINGÚN REPOSITORIO. ABORTANDO ENVIO.");
        return;
    }

    recuperacionRepo.deleteByCorreo(correo);

    String codigo = String.format("%06d", new SecureRandom().nextInt(1_000_000));

    RecuperacionPassword recuperacion = RecuperacionPassword.builder()
            .correo(correo)
            .codigo(codigo)
            .fechaCreacion(new Date())
            .build();

    recuperacionRepo.save(recuperacion);
    
    System.out.println(">>> ENVIANDO CORREO A: " + correo + " CON CODIGO: " + codigo);
    emailService.enviarCodigoRecuperacion(correo, nombreUsuario, codigo);
    }

    @Override
    public ResetTokenResponseDto verificarCodigoRecuperacion(VerificarCodigoRecuperacionDto dto) {
        String correo = dto.getCorreo() == null ? "" : dto.getCorreo().trim().toLowerCase();
        String codigo = dto.getCodigo() == null ? "" : dto.getCodigo().trim();

        RecuperacionPassword recuperacion = recuperacionRepo.findByCorreoAndCodigo(correo, codigo)
                .orElseThrow(() -> new IllegalArgumentException("Código incorrecto o ha expirado"));

        // Verificar si el correo pertenece a un cliente o a un restaurante
        boolean existeCliente = clienteRepo.findByCorreo(correo).isPresent();
        boolean existeRestaurante = restauranteRepo.findByCorreo(correo).isPresent();

        if (!existeCliente && !existeRestaurante) {
            recuperacionRepo.delete(recuperacion);
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        // El OTP es de un solo uso. Una vez validado, se elimina.
        recuperacionRepo.delete(recuperacion);

        String resetToken = jwtService.generarResetToken(correo);
        return ResetTokenResponseDto.builder()
                .resetToken(resetToken)
                .build();
    }

    @Override
    public void cambiarPasswordConToken(String resetToken, CambiarPasswordRecuperacionDto dto) {
       if (dto == null || dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("La nueva contraseña es obligatoria");
        }

        if (dto.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
        }

        String correo = jwtService.validarResetTokenYExtraerCorreo(resetToken);

        // 1. Intentar cambiar la contraseña si es un Cliente
        Optional<Cliente> clienteOpt = clienteRepo.findByCorreo(correo);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            ClienteAuth auth = authRepo.findById(cliente.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Credenciales no encontradas para el cliente"));

            auth.setPass(passwordEncoder.encode(dto.getPassword()));
            authRepo.save(auth);
            return; // Termina la ejecución exitosamente
        }

        // 2. Intentar cambiar la contraseña si es un Restaurante
        Optional<Restaurante> restauranteOpt = restauranteRepo.findByCorreo(correo);
        if (restauranteOpt.isPresent()) {
            Restaurante restaurante = restauranteOpt.get();
            
            restaurante.setPassword(passwordEncoder.encode(dto.getPassword()));
            restauranteRepo.save(restaurante);
            return; // Termina la ejecución exitosamente
        }

        // Si no se encontró en ninguno de los dos
        throw new IllegalArgumentException("Usuario no encontrado en el sistema");
    
}
}



