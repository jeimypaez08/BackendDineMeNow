package com.example.BackendDineMeNow.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final  JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter){
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Desactivar seguridad para todas las rutas (nivel de acceso)
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                //rutas publicas
                .requestMatchers("/api/auth/login", 
                                 "/api/clientes/registro",
                                 "/api/verificacion/**", 
                                 "/api/restaurantes/registro/**",
                                 "/api/restaurantes/estado/ACTIVO"
                ).permitAll()
                .requestMatchers(
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "/swagger-ui.html"
                ).permitAll()

                //rutas de clientes
                .requestMatchers(HttpMethod.GET,"/api/clientes").hasAuthority("ROL_ADMIN") //solo admin puede ver todos los clientes
                .requestMatchers("/api/clientes/**").hasAnyAuthority("ROL_ADMIN", "ROL_CLIENTE")

                //rutas de reservas
                //crear o ver reservas del usuario actual
                .requestMatchers("/api/reservas/**").hasAnyAuthority("ROL_ADMIN", "ROL_CLIENTE")

                //rutas de restaurantes
                // El cambio de contraseña obligatorio para activar la cuenta
                .requestMatchers(HttpMethod.PUT, "/api/restaurantes/*/cambiar-password").hasAuthority("ROL_RESTAURANTE")

                // Modificar datos específicos del restaurante (El dueño o el Admin)
                .requestMatchers(HttpMethod.PUT, "/api/restaurantes/**").hasAnyAuthority("ROL_ADMIN", "ROL_RESTAURANTE")
                .requestMatchers(HttpMethod.DELETE, "/api/restaurantes/**").hasAuthority("ROL_ADMIN")

                .requestMatchers("/api/restaurantes/**").hasAnyAuthority("ROL_ADMIN", "ROL_RESTAURANTE", "ROL_CLIENTE")

                //ruta de empleados
                .requestMatchers("/api/empleados/**").hasAuthority("ROL_ADMIN")

                
                //proteccion total
                .anyRequest().authenticated()
            )
            //registrar el filtro
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
