package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.ClienteDto;
import com.example.BackendDineMeNow.models.Cliente;

public interface ClienteMapper {
    Cliente toUsuario(ClienteDto usuarioDto); //Convertir UsuarioDto a Usuario

    ClienteDto toUsuarioDto(Cliente usuario); //Convertir el Usuario a usuarioDto

    List<ClienteDto> toUsuarioDtoList(List<Cliente> usuarios);

    void actualizarUser(ClienteDto usuarioDto, Cliente usuario);

}
