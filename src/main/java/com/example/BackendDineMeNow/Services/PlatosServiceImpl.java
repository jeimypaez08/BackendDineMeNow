package com.example.BackendDineMeNow.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.PlatosDto;
import com.example.BackendDineMeNow.Mapper.PlatosMapper;
import com.example.BackendDineMeNow.models.Categorias;
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
public List<PlatosDto> listarPlatos() {
    return platosMapper.toPlatosDtoList(platosRepository.findAll());
}

//Listar platos por restaurante
@Override
public List<PlatosDto> listarPorRestaurante(String nitRestaurante){
    List<Platos> platos = platosRepository.findByNitRestaurante(nitRestaurante);//El método del repositorio busca platos por nit del restaurante y devuelve una lista de platos. Luego convertimos esa lista de platos a una lista de platos DTOs y la retornamos.
    return platosMapper.toPlatosDtoList(platos);
}

//Listar platos por nombre
@Override
public List<PlatosDto> listarPorNombre(String nomPlato){
    List<Platos> platos = platosRepository.findByNomPlatoContainingIgnoreCase(nomPlato);//IgnoreCase: Evita que el buscador falle si el usuario escribe en minúsculas y nosotros guardamos en mayúsculas.
    return platosMapper.toPlatosDtoList(platos);
}

//Listar platos por categoria
@Override
public List<PlatosDto> listarPorCategoria(List<Categorias> categ){
    List<Platos> platos = platosRepository.findByCategIn(categ);
    return platosMapper.toPlatosDtoList(platos);
}

//Listar platos por nombre y precio máximo
@Override
public List<PlatosDto> listarPorNombreYPrecioMax(String nomPlato, double precioMax){//Recibimos ambos filtros como parámetros, llamamos al método del repositorio que hace la consulta con ambos filtros y convertimos el resultado a DTOs.
    //si alguien pone un precio negativo, lo convertimos a 0
    if(precioMax < 0){
        precioMax = 0;
    }

    List<Platos> platos = platosRepository.findByNomPlatoContainingIgnoreCaseAndPrecioLessThanEqual(nomPlato, precioMax);//El método del repositorio busca platos que contengan el nombre (ignorando mayúsculas y minúsculas) y que tengan un precio menor o igual al valor enviado.
    return platosMapper.toPlatosDtoList(platos);
}

//Actualizar plato
@Override
public PlatosDto actPlatos(String id, PlatosDto platosDto) {//Buscamos el plato por id, si no lo encontramos lanzamos una excepción.
    Platos platos = platosRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Id de plato no encontrado, id: " + id));
    platosMapper.actualizarPlatos(platosDto, platos);
    return platosMapper.toPlatosDto(platosRepository.save(platos));
}

//Actualizar plato por nit restaurante y nombre
@Override
public PlatosDto actualizarPlatosPorNitRestauranteAndNombre(String nitRestaurante, String nomPlato, PlatosDto platosDto) {//Buscamos el plato por nit restaurante y nombre, si no lo encontramos lanzamos una excepción.
    Platos platos = platosRepository.findByNitRestauranteAndNomPlato(nitRestaurante, nomPlato)
        .orElseThrow(() -> new RuntimeException("Plato no encontrado, nitRestaurante: " + nitRestaurante + ", nomPlato: " + nomPlato));//Si lo encontramos, actualizamos sus datos con los datos del DTO y lo guardamos.
   
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

//eliminar plato por restaurante y nombre
@Override
public void borrarPlatosPorNitRestauranteAndNombre(String nitRestaurante, String nomPlato){
    platosRepository.deleteByNitRestauranteAndNomPlato(nitRestaurante, nomPlato);
}
}
