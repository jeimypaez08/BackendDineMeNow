package com.example.BackendDineMeNow;

import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.BackendDineMeNow.models.Admin;
import com.example.BackendDineMeNow.repositories.AdminRepository;

@SpringBootApplication
public class BackendDineMeNowApplication {

	public static void main(String[] args) { 
		SpringApplication.run(BackendDineMeNowApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(AdminRepository adminrepo, PasswordEncoder encoder){
		return args ->{
			//si el correo no existe en la coleccion admin
			if(adminrepo.findByCorreo("dinemenow2@gmail.com").isEmpty()){
				Admin admin = new Admin();
				admin.setCorreo("dinemenow2@gmail.com");
				admin.setPassword(encoder.encode("admin123*"));
				admin.setRol(Collections.singleton("ROL_ADMIN"));

				adminrepo.save(admin);
				System.out.println(" USUARIO ADMINISTRADOR CREADO EN MONGO");
			}
		};
	}

}
