package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.UsuarioDto;
import com.example.BackendDineMeNow.models.Usuario;

public interface UsuarioMapper {
    Usuario toUsuario(UsuarioDto usuarioDto); //Convertir UsuarioDto a Usuario

    UsuarioDto toUsuarioDto(Usuario usuario); //Convertir el Usuario a usuarioDto

    List<UsuarioDto> toUsuarioDtoList(List<Usuario> usuarios);

    void actualizarUser(UsuarioDto usuarioDto, Usuario usuario);

}
