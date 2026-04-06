package com.example.BackendDineMeNow.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.MesaDto;
import com.example.BackendDineMeNow.Mapper.MesaMapper;
import com.example.BackendDineMeNow.models.Mesa;
import com.example.BackendDineMeNow.repositories.MesaRepository;

@Service
public class MesaServiceImpl implements MesaService{
    private final MesaRepository mesaRepository;

    private final MesaMapper  mesaMapper;

    public MesaServiceImpl(MesaRepository mesaRepository, MesaMapper mesaMapper) {
        this.mesaRepository = mesaRepository;
        this.mesaMapper = mesaMapper;
    }

    // Crear mesa
    @Override
    public MesaDto crearMesa(MesaDto mesaDto) {
        Mesa mesa = mesaMapper.toMesa(mesaDto);
        return mesaMapper.toMesaDto(mesaRepository.save(mesa));
    }

    // Listar mesas
    @Override
    public List<MesaDto> ListaMesas(){
        return mesaMapper.toMesaDtoList(mesaRepository.findAll());
    }

    // Listar mesas por restaurante
    @Override
    public List<MesaDto> listarPorRestaurante(String nitRestaurante){
        List<Mesa> mesas = mesaRepository.findByNitRestaurante(nitRestaurante);
        return mesaMapper.toMesaDtoList(mesas);
    }

    // Actualizar mesa
    @Override
    public MesaDto actMesa(String id, MesaDto mesaDto) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id de mesa no encontrado, id: " + id));
        mesaMapper.actualizarMesa(mesaDto, mesa);
        return mesaMapper.toMesaDto(mesaRepository.save(mesa));
    }

    // Eliminar mesa
    @Override
    public void borrarMesa(String id) {
        if (!mesaRepository.existsById(id)) {
            throw new RuntimeException("Id de mesa no encontrado, id: " + id);
        }
        mesaRepository.deleteById(id);
    }

    // Eliminar mesa por nit restaurante y numero de mesa
    @Override
    public void borrarMesaPorNitRestauranteAndNum(String nitRestaurante, String numMesa){
        mesaRepository.deleteByNitRestauranteAndNumMesa(nitRestaurante, numMesa);
    
    // Agregar un mensaje de log para verificar que se está llamando al método
    System.out.println("Intento de borrado de mesa " + numMesa + " del NIT: " + nitRestaurante);
    }

    }

