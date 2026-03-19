package com.example.BackendDineMeNow.Mapper;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.PlatosDto;
import com.example.BackendDineMeNow.models.Platos;

@Component
public class PlatosMapperImpl implements PlatosMapper{
@Override
    public Platos toPlatos(PlatosDto platosDto) {
        if (platosDto == null) {
            return null;
        }
        return Platos.builder()
            .id(platosDto.getId())
            .nom(platosDto.getNombre())
            .desc(platosDto.getDescripcion())
            .precio(platosDto.getPrecio())
            .build();
    }
    @Override
    public PlatosDto toPlatosDto(Platos platos) {
        if (platos == null) {
            return null;
            
}

}
    }
    


