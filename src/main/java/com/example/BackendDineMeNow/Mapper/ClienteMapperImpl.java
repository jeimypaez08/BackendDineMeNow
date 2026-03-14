package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.ClienteDto;
import com.example.BackendDineMeNow.models.Cliente;

@Component
public class ClienteMapperImpl implements ClienteMapper{
    @Override
    public Cliente toUsuario(ClienteDto usuarioDto) {
        if (usuarioDto == null) {
            return null;
        }
        return Cliente.builder()
            .id(usuarioDto.getId())
            .nom(usuarioDto.getNombre())
            .ape(usuarioDto.getApellido())
            .doc(usuarioDto.getDocumento())
            .dir(usuarioDto.getDireccion())
            .correo(usuarioDto.getEmail())
            .build();
    }

    @Override
    public ClienteDto toUsuarioDto(Cliente usuario) {
        if (usuario == null) {
            return null;
        }
        return ClienteDto.builder()
            .id(usuario.getId())
            .nombre(usuario.getNom())
            .apellido(usuario.getApe())
            .documento(usuario.getDoc())
            .direccion(usuario.getDir())
            .email(usuario.getCorreo())
            .build();
    }

    //Lista de usuarios
    @Override
    public List<ClienteDto> toUsuarioDtoList(List<Cliente> usuarios) {
        if (usuarios == null) {
            return null;
        }

        return usuarios.stream()
            .map(this::toUsuarioDto)
            .toList();
    }

    @Override
    public void actualizarUser(ClienteDto usuarioDto, Cliente usuario) {
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
        usuario.setDoc(usuarioDto.getDocumento());
        usuario.setDir(usuarioDto.getDireccion());
        usuario.setCorreo(usuarioDto.getEmail());
    }

}
