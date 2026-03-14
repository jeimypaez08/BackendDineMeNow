package com.example.BackendDineMeNow.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.ClienteDto;
import com.example.BackendDineMeNow.Dtos.ClienteRegistroDto;
import com.example.BackendDineMeNow.Mapper.ClienteMapper;
import com.example.BackendDineMeNow.models.Cliente;
import com.example.BackendDineMeNow.models.ClienteAuth;
import com.example.BackendDineMeNow.repositories.ClienteAuthRepository;
import com.example.BackendDineMeNow.repositories.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final PasswordEncoder passwordEncoder;

    private final ClienteRepository usuarioRepository;

    private final ClienteMapper usuarioMapper;

    private final ClienteAuthRepository authRepo;

    public ClienteServiceImpl(ClienteRepository usuarioRepository, ClienteAuthRepository authRepo, ClienteMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.authRepo = authRepo;
    }

    //Crear un usuario
    @Override
    public ClienteDto crearUser(ClienteDto usuarioDto) {
        Cliente usuario = usuarioMapper.toUsuario(usuarioDto);
        return usuarioMapper.toUsuarioDto(usuarioRepository.save(usuario));
    }

    //Listar usuarios
    @Override
    public List<ClienteDto> ListaUsers() {
        return usuarioMapper.toUsuarioDtoList(usuarioRepository.findAll());
    }

    //Actualizar usuario
    @Override
    public ClienteDto actUser(String id, ClienteDto usuarioDto) {
        Cliente usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Id de usuario no encontrado, id: " + id));
        usuarioMapper.actualizarUser(usuarioDto, usuario);
        return usuarioMapper.toUsuarioDto(usuarioRepository.save(usuario));
    }

    //Eliminar
    @Override
    public void borrarUser(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Id de usuario no encontrado, id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public ClienteRegistroDto registerUser(ClienteRegistroDto usuarioRegistroDto) {
        //Guardar el perfil
        Cliente perfil = Cliente.builder()
            .id(UUID.randomUUID().toString()) //Mismo id para perfil y auth
            .nom(usuarioRegistroDto.getNombre())
            .ape(usuarioRegistroDto.getApellido())
            .correo(usuarioRegistroDto.getEmail())
            .build();

        Cliente perfilGuardado = usuarioRepository.save(perfil);

        //Guardar el auth
        ClienteAuth auth = new ClienteAuth();
        auth.setId(perfilGuardado.getId());
        auth.setUser(usuarioRegistroDto.getUser());
        auth.setPass(passwordEncoder.encode(usuarioRegistroDto.getPassword())); //Encriptar
        auth.setRoles(usuarioRegistroDto.getRoles());

        authRepo.save(auth);

        return usuarioRegistroDto;
    }


}
