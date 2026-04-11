package com.example.BackendDineMeNow.security;


import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

@Service
public class JwtService {

    @Value("${JWT_SECRET}")
    private String SECRET;

    private SecretKey getkey(){
        //convertir el SECRET en una llave real para HS256
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generarToken(String username){
        return Jwts.builder()
                .subject(username)//define nombre de usuario dentro del token
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
}