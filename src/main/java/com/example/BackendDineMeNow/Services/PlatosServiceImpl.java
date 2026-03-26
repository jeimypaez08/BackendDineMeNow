package com.example.BackendDineMeNow.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.PlatosDto;
import com.example.BackendDineMeNow.Mapper.PlatosMapper;
import com.example.BackendDineMeNow.models.Platos;
import com.example.BackendDineMeNow.repositories.PlatosRepository;;

@Service
public class PlatosServiceImpl  implements PlatosService {
private final PlatosRepository platosRepository;
private final PlatosMapper platosMapper;
public PlatosServiceImpl(PlatosRepository platosRepository, PlatosMapper platosmapper){
    this.platosRepository=platosRepository;
    this.platosMapper=platosmapper;
}

//Crear un plato
@Override
public PlatosDto crearPlatos(PlatosDto platosDto){
    Platos platos = platosMapper.toPlatos(platosDto);
    return platosMapper.toPlatosDto(platosRepository.save(platos));
}

//Listar platos
@Override
public List<PlatosDto> ListaPlatos() {
    return platosMapper.toPlatosDtoList(platosRepository.findAll());
}

//Actualizar plato
@Override
public PlatosDto actPlatos(String id, PlatosDto platosDto) {
    Platos platos = platosRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Id de plato no encontrado, id: " + id));
    platosMapper.actualizarPlatos(platosDto, platos);
    return platosMapper.toPlatosDto(platosRepository.save(platos));
}

//Eliminar plato
@Override
public void borrarPlatos(String id) {
if(!platosRepository.existsById(id)){
    throw new RuntimeException("Id de plato no encontrado, id: " + id);
}
platosRepository.deleteById(id);

}
}
