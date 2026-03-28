package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.ClienteDto;
import com.example.BackendDineMeNow.models.Cliente;

@Component
public class ClienteMapperImpl implements ClienteMapper{

    @Override
    public Cliente toCliente(ClienteDto clienteDto) {
        if (clienteDto == null) {
            return null;
        }
        return Cliente.builder()
            .id(clienteDto.getId())
            .nombre(clienteDto.getNombre())
            .apellido(clienteDto.getApellido())
            .documento(clienteDto.getDocumento())
            .direccion(clienteDto.getDireccion())
            .correo(clienteDto.getCorreo())
            .telefono(clienteDto.getTelefono())
            .foto(clienteDto.getFoto())
            .build();
    }

    @Override
    public ClienteDto toClienteDto(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        return ClienteDto.builder()
            .id(cliente.getId())
            .nombre(cliente.getNombre())
            .apellido(cliente.getApellido())
            .documento(cliente.getDocumento())
            .direccion(cliente.getDireccion())
            .correo(cliente.getCorreo())
            .telefono(cliente.getTelefono())
            .foto(cliente.getFoto())
            .build();
    }

    //Lista de usuarios
    @Override
    public List<ClienteDto> toClienteDtoList(List<Cliente> clientes) {
        if (clientes == null) {
            return null; // Si la lista de clientes es nula, devuelve nulo
        }

        return clientes.stream()//convierte la lista en un flujo de datos para poder procesarla elemento por elemento

            .map(this::toClienteDto)//aplica la función toClienteDto a cada elemento del flujo, convirtiendo cada Cliente en un ClienteDto
            
            .toList();//convierte el flujo de datos de nuevo en una lista y la devuelve como resultado
    }

    @Override
    public void actualizarCliente(ClienteDto clienteDto, Cliente cliente) {
        //Verificar que no esten vacios
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente esta vacio");
        }
        if (clienteDto == null) {
            throw new IllegalArgumentException("El dto del cliente esta vacio");
        }

        //Actualizar solo los campos que vienen con valor
        if (clienteDto.getNombre() != null) cliente.setNombre(clienteDto.getNombre());
        if (clienteDto.getApellido() != null) cliente.setApellido(clienteDto.getApellido());
        if (clienteDto.getDocumento() != null) cliente.setDocumento(clienteDto.getDocumento());
        if (clienteDto.getDireccion() != null) cliente.setDireccion(clienteDto.getDireccion());
        if (clienteDto.getCorreo() != null) cliente.setCorreo(clienteDto.getCorreo());
        if (clienteDto.getTelefono() != null) cliente.setTelefono(clienteDto.getTelefono());
        if (clienteDto.getFoto() != null) cliente.setFoto(clienteDto.getFoto());
    }

}
