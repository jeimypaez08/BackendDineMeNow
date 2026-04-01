package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.ClienteDto;
import com.example.BackendDineMeNow.models.Cliente;

public interface ClienteMapper {
    Cliente toCliente(ClienteDto clienteDto); //Convertir ClienteDto a Cliente

    ClienteDto toClienteDto(Cliente cliente); //Convertir el Cliente a clienteDto

    List<ClienteDto> toClienteDtoList(List<Cliente> clientes);

    void actualizarCliente(ClienteDto clienteDto, Cliente cliente);

}
