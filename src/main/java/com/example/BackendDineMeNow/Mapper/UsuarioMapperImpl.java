package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.UsuarioDto;
import com.example.BackendDineMeNow.models.Usuario;

@Component
public class UsuarioMapperImpl implements UsuarioMapper{
    @Override
    public Usuario toUsuario(UsuarioDto usuarioDto) {
        if (usuarioDto == null) {
            return null;
        }
        return Usuario.builder()
            .id(usuarioDto.getId())
            .nom(usuarioDto.getNombre())
            .ape(usuarioDto.getApellido())
            .correo(usuarioDto.getEmail())
            .build();
    }

    @Override
    public UsuarioDto toUsuarioDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return UsuarioDto.builder()
            .id(usuario.getId())
            .nombre(usuario.getNom())
            .apellido(usuario.getApe())
            .email(usuario.getCorreo())
            .build();
    }

    //Lista de usuarios
    @Override
    public List<UsuarioDto> toUsuarioDtoList(List<Usuario> usuarios) {
        if (usuarios == null) {
            return null;
        }

        return usuarios.stream()
            .map(this::toUsuarioDto)
            .toList();
    }

    @Override
    public void actualizarUser(UsuarioDto usuarioDto, Usuario usuario) {
        //Verificar que no esten vacios
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario esta vacio");
        }
        if (usuarioDto == null) {
            throw new IllegalArgumentException("El dto del usuario esta vacio");
        }

        //Actualzar la entidad
        usuario.setNom(usuarioDto.getNombre());
        usuario.setApe(usuarioDto.getApellido());
        usuario.setCorreo(usuarioDto.getEmail());
    }

}
