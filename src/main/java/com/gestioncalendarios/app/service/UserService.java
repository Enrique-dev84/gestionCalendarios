package com.gestioncalendarios.app.service;

import com.gestioncalendarios.app.dto.UserDTO;
import com.gestioncalendarios.app.exception.DuplicateUserException;
import com.gestioncalendarios.app.persistence.entity.RolEntity;
import com.gestioncalendarios.app.persistence.entity.RolEnum;
import com.gestioncalendarios.app.persistence.entity.UserEntity;
import com.gestioncalendarios.app.persistence.repository.RolRepository;
import com.gestioncalendarios.app.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Crear un nuevo usuario
    public UserEntity createUser(UserDTO userDTO) {
        // Verificar si el usuario ya existe
        if (userRepository.findUserEntityByUsername(userDTO.getUsername()).isPresent()) {
            throw new DuplicateUserException("El nombre de usuario ya está en uso.");
        }

        // Crear la entidad del usuario
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(encryptPassword(userDTO.getPassword())); // Encripta la contraseña
        userEntity.setNombre(userDTO.getNombre());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());
        userEntity.setRecibirNotificacionesTurnos(userDTO.isRecibirNotificacionesTurnos());

        // Valores por defecto
        userEntity.setEnabled(true);
        userEntity.setAccountNoExpired(true);
        userEntity.setAccountNoLocked(true);
        userEntity.setCredentialNoExpired(true);

        // Buscar el rol por su nombre
        RolEntity rolTrabajador = rolRepository.findByRolEnum(RolEnum.TRABAJADOR)
                .orElseThrow(() -> new IllegalArgumentException("Rol TRABAJADOR no encontrado"));

        // Verificar si el rol ya está asignado al usuario
        if (!userEntity.getRolList().contains(rolTrabajador)) {
            userEntity.getRolList().add(rolTrabajador); // Asignar el rol por defecto
        }

        // Guardar el usuario y devolverlo
        return userRepository.save(userEntity);
    }

    // Encontrar usuario por su nombre de usuario
    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findUserEntityByUsername(username);
    }

    // Deshabilitar un usuario (eliminación lógica)
    @Transactional
    public void disableUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setEnabled(false); // Cambiar el estado del usuario a deshabilitado
        userRepository.save(user); // Guardar los cambios
    }

    // Actualizar la información del usuario
    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    // Método auxiliar para encriptar contraseñas
    private String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }


}


