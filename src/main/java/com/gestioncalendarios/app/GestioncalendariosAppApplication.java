package com.gestioncalendarios.app;

import com.gestioncalendarios.app.persistence.entity.PermissionEntity;
import com.gestioncalendarios.app.persistence.entity.RolEntity;
import com.gestioncalendarios.app.persistence.entity.RolEnum;
import com.gestioncalendarios.app.persistence.entity.UserEntity;
import com.gestioncalendarios.app.persistence.repository.PermissionRepository;
import com.gestioncalendarios.app.persistence.repository.RolRepository;
import com.gestioncalendarios.app.persistence.repository.UserRepository;
import com.gestioncalendarios.app.service.RolService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Set;


@SpringBootApplication
public class GestioncalendariosAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestioncalendariosAppApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RolService rolService, RolRepository rolRepository, UserRepository userRepository) {
		return args -> {

			rolService.createRoles();


			// Verificar si ya existe el usuario 'admin'
			if (userRepository.findUserEntityByUsername("admin").isEmpty()) {

				// Crear Permission
				PermissionEntity allPermission = PermissionEntity.builder()
						.name("ALL")
						.build();

				// Crear Rol
				RolEntity jefeRol = RolEntity.builder()
						.rolEnum(RolEnum.JEFE)
						.permissionList(Set.of(allPermission))
						.build();

				// Crear Usuarios
				UserEntity userAdmin = UserEntity.builder()
						.username("admin")
						.password("$2b$12$DS5HlmRVW9QOC260APwxpuMAPpsqNGHZznN8J5LosTmFY/UPTqn5O")
						.isEnabled(true)
						.accountNoExpired(true)
						.accountNoLocked(true)
						.credentialNoExpired(true)
						.rolList(Set.of(jefeRol))
						.build();

				userRepository.saveAll(List.of(userAdmin));

			} else {
				System.out.println("El usuario 'admin' ya existe.");
			}
		};
	}


}



