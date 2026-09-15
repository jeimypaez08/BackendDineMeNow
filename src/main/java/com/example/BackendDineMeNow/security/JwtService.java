package com.example.BackendDineMeNow.security;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.BackendDineMeNow.models.Rol;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {

    @Value("${JWT_SECRET}")
    private String SECRET;

    private SecretKey getkey(){
        //convertir el SECRET en una llave real para HS256
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generarToken(String username, List<Rol> roles){

        List<String> rolesStr = roles.stream()
        .map(Enum::name)
        .collect(Collectors.toList());

        return Jwts.builder()
                .subject(username)//define nombre de usuario dentro del token
                .claim("roles", rolesStr)
                .issuedAt(new Date())//define la fecha de creacion
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 *60))//define la fecha de expiracion, 1 hora de vida
                .signWith(getkey())//metodo de encriptacion
                .compact();//metodo de compactacion

    }


    //leer token y extraer usuario
    public String extraerUsuario(String token){
        return Jwts.parser()
        .verifyWith(getkey()) //clave para vaidar
        .build()//construir al recorrer
        .parseSignedClaims(token)//decodificar token
        .getPayload()//extraer usuario, obtenemos cuerpo de Jwt
        .getSubject();//extraer nombre de usuario, username
        
    }

    //extraer roles
    @SuppressWarnings("unchecked")
    public List<String> extraerRoles(String token){
        return (List<String>) Jwts.parser()
        .verifyWith(getkey()) //clave para vaidar
        .build()//construir al recorrer
        .parseSignedClaims(token)//decodificar token
        .getPayload()
        .get("roles", List.class);
}
  public String validarResetTokenYExtraerCorreo(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String purpose = claims.get("purpose", String.class);
        if (!"RESET_PASSWORD".equals(purpose)) {
            throw new IllegalArgumentException("Token de recuperación inválido");
        }

        String correo = claims.getSubject();
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("Token de recuperación inválido");
        }

        return correo;
    }

    public boolean esResetToken(String token){
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getkey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return "RESET_PASSWORD".equals(claims.get("purpose", String.class));
        } catch (Exception e) {
            return false;
        }
    }
    public String generarResetToken(String correo) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("purpose", "RESET_PASSWORD");

    return Jwts.builder()
            .claims(claims)
            .subject(correo)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .signWith(getkey())
            .compact();
}

public String validarResetTokenYExtraerCorreo1(String token) {
    try {
        Claims claims = Jwts.parser()
                .verifyWith(getkey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String purpose = claims.get("purpose", String.class);

        if (!"RESET_PASSWORD".equals(purpose)) {
            throw new IllegalArgumentException("Token no válido para recuperación de contraseña");
        }

        if (claims.getExpiration().before(new Date())) {
            throw new IllegalArgumentException("El token de recuperación ha expirado");
        }

        return claims.getSubject();

    } catch (Exception e) {
        throw new IllegalArgumentException("Token de recuperación inválido o expirado");
    }
}
}