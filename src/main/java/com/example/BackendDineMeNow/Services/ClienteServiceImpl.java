package com.example.BackendDineMeNow.Services;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.ClienteDto;
import com.example.BackendDineMeNow.Dtos.ClienteRegistroDto;
import com.example.BackendDineMeNow.Mapper.ClienteMapper;
import com.example.BackendDineMeNow.models.Cliente;
import com.example.BackendDineMeNow.models.ClienteAuth;
import com.example.BackendDineMeNow.models.Rol;
import com.example.BackendDineMeNow.models.VerificacionRegistro;
import com.example.BackendDineMeNow.repositories.ClienteAuthRepository;
import com.example.BackendDineMeNow.repositories.ClienteRepository;
import com.example.BackendDineMeNow.repositories.VerificacionRepository;
import com.example.BackendDineMeNow.Services.EmailService;



@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepo;

    private final ClienteMapper clienteMapper;

    private final ClienteAuthRepository authRepo;

    private final VerificacionRepository verificacionRepo;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;



    public ClienteServiceImpl(ClienteRepository clienteRepo, 
                              ClienteAuthRepository authRepo, 
                              ClienteMapper clienteMapper, 
                              VerificacionRepository verificacionRepo,
                              EmailService emailService,
                              PasswordEncoder passwordEncoder) {
        this.clienteRepo = clienteRepo;
        this.authRepo = authRepo;
        this.clienteMapper = clienteMapper;
        this.verificacionRepo = verificacionRepo;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        
    }

    //Registro completo: perfil + auth
    @Override
    public ClienteRegistroDto registrarCliente(ClienteRegistroDto dto) {
        //Validar duplicados
        if(clienteRepo.existsByCorreo(dto.getCorreo())){
            throw new RuntimeException("Correo ya registrado");
        }
        if(authRepo.existsByUser(dto.getUser())){
            throw new RuntimeException("El usuario está en uso");
        }

        //Guardar el perfil
        Cliente perfil = Cliente.builder()
        .nombreCliente(dto.getNombre())
        .apellido(dto.getApellido())
        .documento(dto.getDocumento())
        .direccion(dto.getDireccion())
        .correo(dto.getCorreo())
        .telefono(dto.getTelefono())
        .foto(dto.getFoto())
        .build();

        Cliente perfilGuardado = clienteRepo.save(perfil);

        //Guardar el auth con mismo id y rol fijo
        ClienteAuth auth = new ClienteAuth();
        auth.setId(perfilGuardado.getId());
        auth.setUser(dto.getUser());
        auth.setPass(passwordEncoder.encode(dto.getPassword()));//Encriptar
        auth.setRoles(List.of(Rol.ROL_CLIENTE)); //Rol fijo

        authRepo.save(auth);

        String codigo = generarCodigoVerificacion(6);
        verificacionRepo.deleteByCorreo(dto.getCorreo());
        VerificacionRegistro verificacion = VerificacionRegistro.builder()
                .correo(dto.getCorreo())
                .codigo(codigo)
                .fechaCreacion(new Date())
                .build();
        verificacionRepo.save(verificacion);

        emailService.enviarCodigoVerificacion(dto.getCorreo(), dto.getNombre(), codigo);

        return dto;
    }

    private String generarCodigoVerificacion(int length) {
        int minimo = (int) Math.pow(10, length - 1);
        int maximo = (int) Math.pow(10, length) - 1;
        return String.valueOf(ThreadLocalRandom.current().nextInt(minimo, maximo + 1));
    }


    //Crear solo perfil (sin auth, para admin)
    @Override
    public ClienteDto crearCliente(ClienteDto dto) {
        Cliente cliente = clienteMapper.toCliente(dto); //Convertir DTO a entidad
        return clienteMapper.toClienteDto(clienteRepo.save(cliente));//Guardar y convertir de nuevo a DTO
    }


    //Listar usuarios
    @Override
    public List<ClienteDto> listarClientes() {
        return clienteMapper.toClienteDtoList(clienteRepo.findAll());
    }

    //Obtener por id (búsqueda interna) y numero de documento (búsqueda externa)
    @Override
    public ClienteDto obtenerClientePorId(String id) {
        Cliente cliente = clienteRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Id de cliente no encontrado, id: " + id));
        return clienteMapper.toClienteDto(cliente);
    }

    @Override
public ClienteDto obtenerClientePorDocumento(String numero) {
    Cliente cliente = clienteRepo.findByDocumentoNumero(numero)
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado con documento: " + numero));
    return clienteMapper.toClienteDto(cliente);
}


    //Actualizar cliente
    @Override
    public ClienteDto actualizarCliente(String id, ClienteDto dto) {
        Cliente cliente = clienteRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Id de cliente no encontrado, id: " + id));
        clienteMapper.actualizarCliente(dto, cliente);
        return clienteMapper.toClienteDto(clienteRepo.save(cliente));
    }

    //Eliminar
    @Override
    public void eliminarCliente(String id) {
        if (!clienteRepo.existsById(id)) {
            throw new RuntimeException("Id de cliente no encontrado, id: " + id);
        }
        clienteRepo.deleteById(id);
    }



}
