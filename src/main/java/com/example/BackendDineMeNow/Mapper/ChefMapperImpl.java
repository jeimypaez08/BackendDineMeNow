package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.ChefDto;
import com.example.BackendDineMeNow.models.Chef;

@Component
public class ChefMapperImpl implements ChefMapper {

    @Override
    public Chef toChef(ChefDto chefDto){
        if (chefDto == null){
            return null;
        }
    return Chef.builder()
        .id(chefDto.getId())
        .nitRestaurante(chefDto.getNitRestaurante())
        .build();
}
    @Override
    public ChefDto toChefDto(Chef chef){
        if (chef == null){
            return null;
        }
        return ChefDto.builder()
        .id(chef.getId())
        .nitRestaurante(chef.getNitRestaurante())
        .build();
    }

    @Override
    public List<ChefDto> toChefDtoList(List<Chef> chefs){
        if (chefs == null){
            return null;
        }
        return chefs.stream()
        .map(this::toChefDto)
        .toList();
    }

    @Override
    public void actualizarChef(ChefDto chefDto, Chef chef){
        if (chef == null){
            throw new IllegalArgumentException("El chef esta vacio");
        }
        if (chefDto == null){
            throw new IllegalArgumentException("El chefDto esta vacio");
        }
        
        if (chefDto.getNitRestaurante() != null){
            chef.setNitRestaurante(chefDto.getNitRestaurante());
        }
    }
}
