package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.UsuarioDto;

public interface UsuarioService {

    UsuarioDto crearUser(UsuarioDto usuarioDto);

    List<UsuarioDto> ListaUsers();

    UsuarioDto actUser(String id, UsuarioDto usuarioDto);

    void borrarUser(String id);

}
