package com.example.BackendDineMeNow.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.ChefDto;
import com.example.BackendDineMeNow.Mapper.ChefMapper;
import com.example.BackendDineMeNow.models.Chef;
import com.example.BackendDineMeNow.repositories.ChefRepository;

@Service
public class ChefServiceImpl implements ChefService {

    private final ChefRepository chefRepository;
    private final ChefMapper chefMapper;
    public ChefServiceImpl(ChefRepository chefRepository, ChefMapper chefMapper) {
        this.chefRepository = chefRepository;
        this.chefMapper = chefMapper;
    }

    //crear un chef
    @Override
    public ChefDto crearChef(ChefDto chefDto) {
    Chef chef = chefMapper.toChef(chefDto);
    return chefMapper.toChefDto(chefRepository.save(chef));
    }
//LIstar Chefs
    @Override
    public List<ChefDto> obtenerTodosLosChefs() {
        return chefMapper.toChefDtoList(chefRepository.findAll());
    }
// actualizar
    @Override
    public ChefDto actualizarChef(String id, ChefDto chefDto) {
        Chef chef = chefRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Id de chef no encontrado, id: " + id));
        chefMapper.actualizarChef(chefDto, chef);
        return chefMapper.toChefDto(chefRepository.save(chef));
}
//Eliminar
    @Override
    public void eliminarChef(String id) {
        if (!chefRepository.existsById(id)) {
            throw new RuntimeException("Id de chef no encontrado, id: " + id);
        }
        chefRepository.deleteById(id);
    }


}


