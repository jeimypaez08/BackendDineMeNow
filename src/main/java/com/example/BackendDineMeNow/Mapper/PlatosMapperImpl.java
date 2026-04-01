package com.example.BackendDineMeNow.Mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.Dtos.PlatosDto;
import com.example.BackendDineMeNow.models.Platos;

@Component
public class PlatosMapperImpl implements PlatosMapper {


    @Override
    public Platos toPlatos(PlatosDto platosDto) {
        if (platosDto == null) {
            return null;
        }

        return Platos.builder()
            .idplatos(platosDto.getId())
            .nomPlatos(platosDto.getNombrePlatos())
            .desc(platosDto.getDescripcion())
            .precio(platosDto.getPrecio())
            .categ(platosDto.getCategoria())
            .build();
    }

    @Override
    public PlatosDto toPlatosDto(Platos platos) {
        if (platos == null) {
            return null;
        }

        return PlatosDto.builder()
            .id(platos.getIdplatos())
            .nombrePlatos(platos.getNomPlatos())
            .descripcion(platos.getDesc())
            .precio(platos.getPrecio())
            .categoria(platos.getCateg())
            .build();
    }

    // Lista de platos
    @Override
    public List<PlatosDto> toPlatosDtoList(List<Platos> platos) {
        if (platos == null) {
            return null;
        }

        return platos.stream()
            .map(this::toPlatosDto)
            .toList();
    }

    @Override
    public void actualizarPlatos(PlatosDto platosDto, Platos platos) {
        // Verificar que no estén vacíos
        if (platos == null) {
            throw new IllegalArgumentException("El plato esta vacio");
        }
        if (platosDto == null) {
            throw new IllegalArgumentException("El platoDto esta vacio");
        }

        // Actualizar entidad
        platos.setNomPlatos(platosDto.getNombrePlatos());
        platos.setDesc(platosDto.getDescripcion());
        platos.setPrecio(platosDto.getPrecio());
        platos.setCateg(platosDto.getCategoria());
    }

}
    


