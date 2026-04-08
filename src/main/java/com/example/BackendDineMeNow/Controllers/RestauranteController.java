package com.example.BackendDineMeNow.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BackendDineMeNow.Dtos.CambiarContraDto;
import com.example.BackendDineMeNow.Dtos.LoginRequestDto;
import com.example.BackendDineMeNow.Dtos.RestauranteDto;
import com.example.BackendDineMeNow.Services.RestauranteService;
import com.example.BackendDineMeNow.models.EstadoRestaurante;





@CrossOrigin(origins = "http://localhost:5173")
@RestController// Anotación para indicar que esta clase es un controlador REST en Spring Boot, lo que permite manejar solicitudes HTTP y devolver respuestas en formato JSON
@RequestMapping ("/api/restaurantes")// Anotación para mapear las solicitudes HTTP a la ruta "/api/restaurantes", lo que significa que todas las solicitudes a esta ruta serán manejadas por los métodos de esta clase
public class RestauranteController {

  private final RestauranteService restauranteService;// Inyección de dependencia del servicio de restaurante a través del constructor

  public RestauranteController(RestauranteService restauranteService) {// Constructor para inyectar el servicio de restaurante
    this.restauranteService = restauranteService;// Asignar el servicio de restaurante al campo de la clase
  }


  //POST /api/restaurantes
  @PostMapping("/registro")// Método para manejar solicitudes POST a la ruta "/api/restaurantes", lo que significa que se utilizará para crear un nuevo restaurante
  public ResponseEntity<?> crearRestaurante(@RequestBody RestauranteDto restauranteDto){// Recibir un objeto RestauranteDto en el cuerpo de la solicitud, crear un nuevo restaurante utilizando el servicio y devolver una respuesta con el restaurante creado y un estado HTTP 201 (CREATED)
    try{
      RestauranteDto nuevo = restauranteService.crearRestaurante(restauranteDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    } catch(RuntimeException e){
      // Esto le enviará al frontend el mensaje "El NIT ya existe" o similar
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  //POST /api/restaurantes/login
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginDto){
    try{
      //si sale bien, devuelve 200 ok con los datos
      return ResponseEntity.ok(restauranteService.login(loginDto));
    } catch(RuntimeException e){
      //si hay error, devuelve 400 bad request con el mensaje de error
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
   
  }
  


  //GET /api/restaurantes
  @GetMapping// Método para manejar solicitudes GET a la ruta "/api/restaurantes", lo que significa que se utilizará para obtener una lista de todos los restaurantes
  public ResponseEntity<List<RestauranteDto>> obtenerRestaurantes() {
      return ResponseEntity.ok(restauranteService.listarRestaurante());
  }

  //GET /api/restaurantes/estado/{estado}
  @GetMapping("/estado/{estado}")// Método para manejar solicitudes GET a la ruta "/api/restaurantes/estado/{estado}", lo que significa que se utilizará para obtener una lista de restaurantes filtrados por su estado
  public ResponseEntity<List<RestauranteDto>> listarPorEstado(@PathVariable EstadoRestaurante estado) {
      return ResponseEntity.ok(restauranteService.listarPorEstado(estado));
  }

  //GET /api/restaurantes/categoria/{categoria}
  @GetMapping("/categoria/{categoria}")
  public ResponseEntity<List<RestauranteDto>> listarPorCategoria(@PathVariable String categoria) {
      return ResponseEntity.ok(restauranteService.listarPorCategoria(categoria));
  }

  //GET /api/restaurantes/{id}
  @GetMapping("/{id}")
  public ResponseEntity<RestauranteDto> obtenerPorId(@PathVariable String id) {// Buscar un restaurante por su ID utilizando el servicio y devolver una respuesta con el restaurante encontrado y un estado HTTP 200 (OK)
      return ResponseEntity.ok(restauranteService.obtenerPorId(id));
  }

  //GET /api/restaurantes/nit/{nit}
  @GetMapping("/nit/{nit}")
  public ResponseEntity<RestauranteDto> obtenerPorNit(@PathVariable String nit) {// Buscar un restaurante por su NIT utilizando el servicio y devolver una respuesta con el restaurante encontrado y un estado HTTP 200 (OK)
      return ResponseEntity.ok(restauranteService.obtenerPorNit(nit));
  }

  //GET /api/resturante/count/estado/{estado}
  @GetMapping("/count/estado/{estado}")
  public ResponseEntity<Long> contarPorEstado(@PathVariable EstadoRestaurante estado ){
    return ResponseEntity.ok(restauranteService.contarPorEstado(estado));
  }
  


  
  //Actualizar Restaurante

  //PUT /api/restaurantes/{id}
  @PutMapping("/{id}")// Método para manejar solicitudes PUT a la ruta "/api/restaurantes/{id}", lo que significa que se utilizará para actualizar un restaurante existente por su ID
    public ResponseEntity<RestauranteDto> actualizar(@PathVariable String id, @RequestBody RestauranteDto restauranteDto){
     return ResponseEntity.ok(restauranteService.actualizarRestaurante(id, restauranteDto));
  }

  //PUT /api/restaurantes/nit/{nit}
  @PutMapping("/nit/{nit}")
    public ResponseEntity<RestauranteDto> actualizarPorNit(@PathVariable String nit, @RequestBody RestauranteDto restauranteDto){// Buscar un restaurante por su NIT, si no se encuentra, lanzar una excepción, actualizar el restaurante utilizando el servicio y devolver una respuesta con el restaurante actualizado y un estado HTTP 200 (OK)
      return ResponseEntity.ok(restauranteService.actualizarPorNit(nit, restauranteDto));// Buscar un restaurante por su NIT, si no se encuentra, lanzar una excepción, actualizar el restaurante utilizando el servicio y devolver una respuesta con el restaurante actualizado y un estado HTTP 200 (OK)
  }


  //Cambiar estado del restaurante (Aprobar o Rechazar)
  
  //PUT /api/restaurantes/nit/{nit}/aprobar
  @PutMapping("/nit/{nit}/aprobar")
  public ResponseEntity<RestauranteDto> aprobar(@PathVariable String nit){
    return ResponseEntity.ok(restauranteService.aprobarRestaurante(nit));
  }

  //PUT /api/restaurantes/nit/{nit}/rechazar
  @PutMapping("/nit/{nit}/rechazar")
  public ResponseEntity<RestauranteDto> rechazar(@PathVariable String nit){
    return ResponseEntity.ok(restauranteService.rechazarRestaurante(nit));
  }


  //Cambiar contraseña del restaurante

  //PUT /api/restaurantes/{id}/cambiar-password
  @PutMapping("/{id}/cambiar-password")
  public ResponseEntity<Void> cambiarPasswordPorId(@PathVariable String id, @RequestBody CambiarContraDto cambiarContraDto) {
    restauranteService.cambiarPasswordid(id, cambiarContraDto.getPasswordActual(), cambiarContraDto.getPasswordNueva());
    return ResponseEntity.noContent().build();
  }


  //PUT /api/restaurantes/nit/{nit}/cambiar-password
  @PutMapping("/nit/{nit}/cambiar-password")
  public ResponseEntity<Void> cambiarPassword(@PathVariable String nit, @RequestBody CambiarContraDto cambiarContraDto) {// Método para manejar solicitudes PUT a la ruta "/api/restaurantes/nit/{nit}/cambiar-password", lo que significa que se utilizará para cambiar la contraseña de un restaurante existente por su NIT
    restauranteService.cambiarPassword(nit, cambiarContraDto.getPasswordActual(), cambiarContraDto.getPasswordNueva());// Cambiar la contraseña del restaurante utilizando el servicio y devolver una respuesta con un estado HTTP 204 (NO CONTENT) si el cambio fue exitoso
    return ResponseEntity.noContent().build();// Devolver una respuesta con un estado HTTP 204 (NO CONTENT) si el cambio fue exitoso
  }

  

  //Borrar Restaurante

  //DELETE /api/restaurantes/{id}
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable String id) {// Verificar si el restaurante existe por su ID, si no existe, lanzar una excepción
    restauranteService.eliminarRestaurante(id);
    return ResponseEntity.noContent().build();
  }

  //DELETE /api/restaurantes/nit/{nit}
  @DeleteMapping("/nit/{nit}")// Eliminar un restaurante por su NIT utilizando el servicio y devolver una respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa
  public ResponseEntity<Void> eliminarPorNit(@PathVariable String nit) {// Verificar si el restaurante existe por su NIT, si no existe, lanzar una excepción
    restauranteService.eliminarPorNit(nit);// Eliminar el restaurante utilizando el servicio y devolver una respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa
    return ResponseEntity.noContent().build();// Devolver una respuesta con un estado HTTP 204 (NO CONTENT) si la eliminación fue exitosa

  }
  
  }
  

