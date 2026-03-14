package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.ClienteDto;
import com.example.BackendDineMeNow.Dtos.ClienteRegistroDto;

public interface ClienteService {

    ClienteDto crearUser(ClienteDto usuarioDto);

    List<ClienteDto> ListaUsers();

    ClienteDto actUser(String id, ClienteDto usuarioDto);

    void borrarUser(String id);

    ClienteRegistroDto registerUser(ClienteRegistroDto usuarioRegistroDto);
}
