package com.example.BackendDineMeNow.Services;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.ClienteDto;
import com.example.BackendDineMeNow.Dtos.ClienteRegistroDto;

public interface ClienteService {

    ClienteRegistroDto registrarCliente(ClienteRegistroDto dto); //registro con auth

    ClienteDto crearCliente(ClienteDto clienteDto); //solo perfil

    List<ClienteDto> listarClientes();

    ClienteDto obtenerClientePorId(String id); //busqueda interna

    ClienteDto obtenerClientePorDocumento(String numero); //busqueda externa
 
    ClienteDto actualizarCliente(String id, ClienteDto clienteDto);

    void eliminarCliente(String id);


}
