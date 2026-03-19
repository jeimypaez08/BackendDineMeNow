package com.example.BackendDineMeNow.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureAlgorithm;

@Service
public class JwtService {

    private final String SECRET = "mi_clave_secreta";

    private Key getkey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generarToken(String username){
        return Jwts.builder()
                .setSubject(username)//define nombre de usuario dentro del token
                .setIssuedAt(new Date())//define la fecha de creacion
                .setExpiration(new Date(System.CurrentTimeMillis() + 1000 * 60 *60))//define la fecha de expiracion, 1 hora de vida
                .signWith(getkey(),SignatureAlgorithm.HS256)//metodo de encriptacion
                .compact();//metodo de compactacion

    }


    //leer token y extraer usuario
    public String extraerUsuario(String token){
        return Jwts.parserBuilder()
        .setSigningKey(getkey()) //clave para vaidar
        .build()//construir al recorrer
        .parseClaimsJsw(token)//decodificar token
        .getBody()//extraer usuario, obtenemos cuerpo de Jwt
        .getSubject();//extraer nombre de usuario, username
    }
}
