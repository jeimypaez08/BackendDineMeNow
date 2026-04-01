package com.example.BackendDineMeNow.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.Dtos.LoginRequestDto;
import com.example.BackendDineMeNow.Dtos.RestauranteDto;
import com.example.BackendDineMeNow.Mapper.RestauranteMapper;
import com.example.BackendDineMeNow.models.EstadoRestaurante;
import com.example.BackendDineMeNow.models.Restaurante;
import com.example.BackendDineMeNow.repositories.RestauranteRepository;

@Service
public class RestauranteServiceImpl implements RestauranteService {

  private final RestauranteRepository restauranteRepo;
  private final RestauranteMapper restauranteMapper; 
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;


  public RestauranteServiceImpl (RestauranteRepository restauranteRepo, 
                                 RestauranteMapper restauranteMapper,
                                 PasswordEncoder passwordEncoder,
                                 EmailService emailService){
    this.restauranteRepo = restauranteRepo;
    this.restauranteMapper = restauranteMapper;
    this.passwordEncoder = passwordEncoder;
    this.emailService = emailService;
  }

//Crear un Restaurante
@Override
public RestauranteDto crearRestaurante (RestauranteDto restauranteDto){
    if(restauranteRepo.existsByNit(restauranteDto.getNit())){
      throw new RuntimeException("El nit ya existe");
    }
    if(restauranteRepo.existsByCorreo(restauranteDto.getCorreo())){
      throw new RuntimeException("El correo ya existe");
    }

    
  Restaurante restaurante = restauranteMapper.toRestaurante(restauranteDto);// Convertir el DTO a una entidad Restaurante

  //estado inicial del restaurante siempre PENDIENTE - el administrador debe aprobarlo para que pueda cambiarlo
  restaurante.setEstado(EstadoRestaurante.PENDIENTE);// Establecer el estado inicial del restaurante como "PENDIENTE"
  restaurante.setMustChangePassword(false);//no se le envian credenciales al restaurante en este punto, el administrador debe aprobarlo primero para que se le envien las credenciales temporales

  Restaurante guardado = restauranteRepo.save(restaurante);

  //notificar al restaurante que su solicitud de registro ha sido recibida
  emailService.enviarConfirmacionRecibido(guardado.getCorreo(), guardado.getNombre());

  return restauranteMapper.toRestauranteDto(guardado);
}

//aprobar- el administrador aprueba el restaurante y se le envía un correo con las credenciales temporales
@Override
public RestauranteDto aprobarRestaurante(String nit){
  Restaurante restaurante = restauranteRepo.findByNit(nit)// Buscar el restaurante por su NIT, si no se encuentra, lanzar una excepción
    .orElseThrow(() -> new RuntimeException("Nit de restaurante no encontrado, nit: " + nit));


  // Generar una contraseña temporal para el restaurante
  String passwordTemporal = generarPasswordTemporal();
  restaurante.setPassword(passwordEncoder.encode(passwordTemporal));// Codificar la contraseña temporal y establecerla en el restaurante
  restaurante.setMustChangePassword(true);// Establecer el indicador para que el restaurante deba cambiar su contraseña en el próximo inicio de sesión
  restaurante.setEstado(EstadoRestaurante.ACTIVO);// Cambiar el estado del restaurante a "ACTIVO"

  //guardar el restaurante en la base de datos
  Restaurante guardado = restauranteRepo.save(restaurante);

  // Enviar un correo electrónico al restaurante con la contraseña temporal
  emailService.enviarCredenciales(guardado.getCorreo(), guardado.getNombre(), passwordTemporal);

  return restauranteMapper.toRestauranteDto(guardado);// Guardar el restaurante en la base de datos y convertirlo de nuevo a DTO para devolverlo
}

//logueo- el restaurante inicia sesión con su correo y contraseña, si las credenciales son correctas, se le permite el acceso, si no, se lanza una excepción
@Override
public RestauranteDto login(LoginRequestDto loginDto){
  //buscar el restaurante por su correo o nit, si no se encuentra, lanzar una excepción
  Restaurante restaurante = restauranteRepo.findByCorreo(loginDto.getIdentificador())
  .or(() -> restauranteRepo.findByNit(loginDto.getIdentificador()))
    .orElseThrow(() -> new RuntimeException("No se encontro un restaurante con el identificador: " + loginDto.getIdentificador()));

    //verificar que el estado del restaurante sea ACTIVO, si no, lanzar una excepción
    if (restaurante.getEstado() != EstadoRestaurante.ACTIVO) {
      throw new RuntimeException("Acceso denegado. El estado actual es: " + restaurante.getEstado());
    }

    //verificar que la contraseña proporcionada coincida con la contraseña almacenada en la base de datos, si no, lanzar una excepción
    if (!passwordEncoder.matches(loginDto.getPassword(), restaurante.getPassword())){
      throw new RuntimeException("Contraseña incorrecta");
    }

    //si las credenciales son correctas, devolver el restaurante convertido a DTO
    return restauranteMapper.toRestauranteDto(restaurante);
}

//rechazar- el administrador rechaza el restaurante y se le envía un correo notificando el rechazo
@Override
public RestauranteDto rechazarRestaurante(String nit){
  Restaurante restaurante = restauranteRepo.findByNit(nit)
      .orElseThrow(() -> new RuntimeException("Restaurante no encontrado, nit: " + nit));

      restaurante.setEstado(EstadoRestaurante.RECHAZADO);
      Restaurante guardado = restauranteRepo.save(restaurante);

      emailService.enviarNotificacionEstado(guardado.getCorreo(), guardado.getNombre(), "RECHAZADO");

      return restauranteMapper.toRestauranteDto(guardado);
}


//listar restaurantes 
@Override
public List<RestauranteDto> listarRestaurante(){
  return restauranteMapper.toRestauranteDtoList(restauranteRepo.findAll());
}

//listar por estado
@Override
public List<RestauranteDto> listarPorEstado(EstadoRestaurante estado){
  return restauranteMapper.toRestauranteDtoList(restauranteRepo.findByEstado(estado));
}

//listar por categoria
@Override
public List<RestauranteDto> listarPorCategoria(String categoria){
  return restauranteMapper.toRestauranteDtoList(restauranteRepo.findByCategoria(categoria));
}

//obtener por id
@Override
public RestauranteDto obtenerPorId(String id){// Buscar el restaurante por su ID, si no se encuentra, lanzar una excepción
  Restaurante restaurante = restauranteRepo.findById(id)
    .orElseThrow(() -> new RuntimeException("Id de restaurante no encontrado, id: " + id));// Convertir el restaurante encontrado a DTO y devolverlo
  return restauranteMapper.toRestauranteDto(restaurante);
}

//obtener por nit
@Override
public RestauranteDto obtenerPorNit(String nit){// Buscar el restaurante por su NIT, si no se encuentra, lanzar una excepción
  Restaurante restaurante = restauranteRepo.findByNit(nit)
    .orElseThrow(() -> new RuntimeException("Nit de restaurante no encontrado, nit: " + nit));
  return restauranteMapper.toRestauranteDto(restaurante);
}


//Actualizar restaurante

//Actualizar por id
@Override
public RestauranteDto actualizarRestaurante(String id, RestauranteDto restauranteDto){
  Restaurante restaurante = restauranteRepo.findById(id)
    .orElseThrow(() -> new RuntimeException("Id de restaurante no encontrado, id: " + id));
    restauranteMapper.actualizarRestaurante(restauranteDto, restaurante);
    return restauranteMapper.toRestauranteDto(restauranteRepo.save(restaurante));  
}

//Actualizar por nit
@Override
public RestauranteDto actualizarPorNit(String nit, RestauranteDto restauranteDto){// Buscar el restaurante por su NIT, si no se encuentra, lanzar una excepción
  Restaurante restaurante = restauranteRepo.findByNit(nit)
    .orElseThrow(() -> new RuntimeException("Nit de restaurante no encontrado, nit: " + nit));
    restauranteMapper.actualizarRestaurante(restauranteDto, restaurante);
    return restauranteMapper.toRestauranteDto(restauranteRepo.save(restaurante));  
}

//cambio de contraseña por parte del restaurante
@Override
public void cambiarPassword(String nit, String passwordActual, String passwordNueva){// Buscar el restaurante por su NIT, si no se encuentra, lanzar una excepción
  Restaurante restaurante = restauranteRepo.findByNit(nit)
    .orElseThrow(() -> new RuntimeException("Nit de restaurante no encontrado, nit: " + nit));
  
  // Verificar que la contraseña actual proporcionada coincida con la contraseña almacenada en la base de datos
  if (!passwordEncoder.matches(passwordActual, restaurante.getPassword())) {
    throw new RuntimeException("La contraseña actual es incorrecta");
  }
    // Codificar la nueva contraseña y actualizar el restaurante
  restaurante.setPassword(passwordEncoder.encode(passwordNueva));
  restaurante.setMustChangePassword(false);// Establecer el indicador para que el restaurante no deba cambiar su contraseña en el próximo inicio de sesión
  restauranteRepo.save(restaurante);// Guardar el restaurante actualizado en la base de datos
}

  //cambio de contraseña por id
  @Override
public void cambiarPasswordid(String id, String passwordActual, String passwordNueva){// Buscar el restaurante por su ID, si no se encuentra, lanzar una excepción
  Restaurante restaurante = restauranteRepo.findById(id)
    .orElseThrow(() -> new RuntimeException("Id de restaurante no encontrado, id: " + id));


    // Verificar que la contraseña actual proporcionada coincida con la contraseña almacenada en la base de datos
  if (!passwordEncoder.matches(passwordActual, restaurante.getPassword())) {
    throw new RuntimeException("La contraseña actual es incorrecta");
  }

  // Codificar la nueva contraseña y actualizar el restaurante
  restaurante.setPassword(passwordEncoder.encode(passwordNueva));
  restaurante.setMustChangePassword(false);// Establecer el indicador para que el restaurante no deba cambiar su contraseña en el próximo inicio de sesión
  restauranteRepo.save(restaurante);// Guardar el restaurante actualizado en la base de datos

}


//Eliminar
//Eliminar por id
@Override
public void eliminarRestaurante(String id){// Verificar si el restaurante existe por su ID, si no existe, lanzar una excepción
  if (!restauranteRepo.existsById(id)) {
    throw new RuntimeException("Id de restaurante no encontrado, id: " + id);
  }
  restauranteRepo.deleteById(id);
}

//Eliminar por nit
@Override
public void eliminarPorNit(String nit){// Buscar el restaurante por su NIT, si no se encuentra, lanzar una excepción
  Restaurante restaurante = restauranteRepo.findByNit(nit)
    .orElseThrow(() -> new RuntimeException("Nit de restaurante no encontrado, nit: " + nit));
  restauranteRepo.deleteById(restaurante.getId());

}

// Método para generar una contraseña temporal aleatoria
private String generarPasswordTemporal() {
  // Aquí implementar la lógica para generar una contraseña temporal segura
  // Por ejemplo, usar una combinación de letras, números y caracteres especiales
  // establecer una longitud mínima para la contraseña temporal
  return "Temp1234!" + UUID.randomUUID().toString().substring(0, 6).toUpperCase(); // Ejemplo de contraseña temporal generada
}
}
