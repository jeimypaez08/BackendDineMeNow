package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import com.example.BackendDineMeNow.Dtos.ChefDto;
import com.example.BackendDineMeNow.models.Chef;

public interface ChefMapper {

    Chef toChef(ChefDto chefDto);

    ChefDto toChefDto(Chef chef);

    List<ChefDto> toChefDtoList(List<Chef> chefs);

    void actualizarChef(ChefDto chefDto, Chef chef);
}