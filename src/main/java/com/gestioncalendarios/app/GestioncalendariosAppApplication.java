package com.gestioncalendarios.app;

import com.gestioncalendarios.app.persistence.entity.PermissionEntity;
import com.gestioncalendarios.app.persistence.entity.RolEntity;
import com.gestioncalendarios.app.persistence.entity.RolEnum;
import com.gestioncalendarios.app.persistence.entity.UserEntity;
import com.gestioncalendarios.app.persistence.repository.UserRepository;
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
	CommandLineRunner init(UserRepository userRepository){
		return args -> {

			// Crear Permission
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();

			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();

			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();

			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();

			// Crear Rol
			RolEntity jefeRol = RolEntity.builder()
					.rolEnum(RolEnum.JEFE)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			RolEntity encargadoNegocioRol = RolEntity.builder()
					.rolEnum(RolEnum.ENCARGADO_NEGOCIO)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			RolEntity encargadoTurnoRol = RolEntity.builder()
					.rolEnum(RolEnum.ENCARGADO_TURNO)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			RolEntity trabajadorRol = RolEntity.builder()
					.rolEnum(RolEnum.TRABAJADOR)
					.permissionList(Set.of(readPermission))
					.build();

			// Crear Usuarios
			UserEntity userEnrique = UserEntity.builder()
					.username("Enrique")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.rolList(Set.of(jefeRol))
					.build();

			UserEntity userJose = UserEntity.builder()
					.username("Jose")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.rolList(Set.of(encargadoNegocioRol))
					.build();

			UserEntity userMaria = UserEntity.builder()
					.username("María")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.rolList(Set.of(encargadoTurnoRol))
					.build();

			UserEntity userFelipe = UserEntity.builder()
					.username("Felipe")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.rolList(Set.of(trabajadorRol))
					.build();

			userRepository.saveAll(List.of(userEnrique, userJose, userMaria, userFelipe));

		};
	}


}
