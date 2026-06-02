package com.example.BackendDineMeNow.security;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.BackendDineMeNow.models.Admin;
import com.example.BackendDineMeNow.repositories.AdminRepository;

@Component
public class AdminInitializer {
    private final AdminRepository adminRepo;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(AdminRepository adminRepo, PasswordEncoder passwordEncoder){
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public void run(String...args) throws Exception {
        String correoAdmin = "admin@dinemenow.com";

        // Verificar si el admin ya existe
        if (adminRepo.findByCorreo(correoAdmin).isEmpty()) {
            Admin admin = new Admin();
            admin.setCorreo(correoAdmin);
            admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña por defecto
            admin.setRol(Set.of("ROL_ADMIN")); //rol de administrador


            adminRepo.save(admin);
            System.out.println(" [DineMeNow] Cuenta de Admin predeterminada creada con éxito.");
        }else{
            System.out.println(" [DineMeNow] Cuenta de Admin ya existe");
        }
    }
}
