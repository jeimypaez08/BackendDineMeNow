package com.example.BackendDineMeNow.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.PlatosDto;
import com.example.BackendDineMeNow.Mapper.PlatosMapper;
import com.example.BackendDineMeNow.models.Platos;
import com.example.BackendDineMeNow.repositories.PlatosRespository;

@Service
public class PlatosServiceImpl {

    private final PlatosRepository platosRepository;

    private final PlatosMapper platosMapper;

    public PlatosServiceImpl(PlatosRespository platosRespository, PlatosMapper platosMapper){
        this.platosRepository = platosRespository;
        this.platosMapper = platosMapper;
    }

    // Crear plato

    @Override
    public List<PlatosDto> crearPlatos(PlatosDto platosDto){
        Platos platos = platosMapper.toPlatos(platosDto);
        return platosMapper.toPlatosDtoList(platosRepository.saveAll(Arrays.asList(platos)));
    }

    // Actualizar plato
    @Override
    public PlatosDto actPlato(String id, PlatosDto platosDto){
        Platos platos = platosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Id de plato no encontrado, id: " + id));
        platosMapper.actualizarPlato(platosDto, platos);
        return platosMapper.toPlatosDto(platosRepository.save(platos));
    }

    // Eliminar plato
    @Override
    public void borrarPlato(String id){
        if (!platosRepository.existsById(id)) {
            throw new RuntimeException("Id de plato no encontrado, id: " + id);
        }
        platosRepository.deleteById(id);

    }
}
