package com.example.BackendDineMeNow.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.UsuarioDto;
import com.example.BackendDineMeNow.Mapper.UsuarioMapper;
import com.example.BackendDineMeNow.models.Usuario;
import com.example.BackendDineMeNow.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    //Crear un usuario
    @Override
    public UsuarioDto crearUser(UsuarioDto usuarioDto) {
        Usuario usuario = usuarioMapper.toUsuario(usuarioDto);
        return usuarioMapper.toUsuarioDto(usuarioRepository.save(usuario));
    }

    //Listar usuarios
    @Override
    public List<UsuarioDto> ListaUsers() {
        return usuarioMapper.toUsuarioDtoList(usuarioRepository.findAll());
    }

    //Actualizar usuario
    @Override
    public UsuarioDto actUser(String id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
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


}
