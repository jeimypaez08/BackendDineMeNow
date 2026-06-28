package com.example.BackendDineMeNow.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService; 

    public JwtAuthenticationFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws jakarta.servlet.ServletException, IOException { // Cambio ServerException por ServletException

            String path = request.getServletPath();

            //si es registro o verificacion, que pase
            if(path.trim().startsWith("/api/auth/login") || 
            path.equals("/api/clientes/registro") ||
            path.startsWith("/api/verificacion") ||
            path.startsWith("/api/restaurantes/registro") ||
            path.startsWith("/api/restaurantes/estado/ACTIVO") ||
            path.startsWith("/swagger-ui") ||
            path.startsWith("/v3/api-docs")
        ){
                filterChain.doFilter(request, response);
        return; // IMPORTANTE: Cortamos la ejecución aquí para que no intente buscar el token
            }

    // 1. Obtener el token del encabezado (Authorization)
    String authHeader = request.getHeader("Authorization");

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        String token = authHeader.substring(7);
        try {
            String username = jwtService.extraerUsuario(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // 1. Extraer los roles que se guardan en el token (ej: ["ROL_ADMIN"])
                List<String> roles = jwtService.extraerRoles(token);

                // 2. Convertir esos Strings en "SimpleGrantedAuthority" para que Spring los entienda
                List<SimpleGrantedAuthority> authorities = roles.stream()
                     .map(SimpleGrantedAuthority::new)
                     .collect(Collectors.toList());
                    
                // 3. Crear el token de autenticación incluyendo las autoridades (permisos)
                UsernamePasswordAuthenticationToken authenticationToken = 
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            // Log de error opcional
            System.out.println("Token inválido: " + e.getMessage());
        }
    }

    // Permite que la petición siga su camino al Controller
    filterChain.doFilter(request, response);
}
}
