package com.gestioncalendarios.app.controller;

import com.gestioncalendarios.app.dto.UserDTO;
import com.gestioncalendarios.app.exception.DuplicateUserException;
import com.gestioncalendarios.app.persistence.entity.UserEntity;
import com.gestioncalendarios.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin-dashboard/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint para crear un nuevo usuario
    @PostMapping("/new")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        // Validación de errores en los datos recibidos
        if (result.hasErrors()) {
            // Construir un mensaje de error legible
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField, // Campo con error
                            FieldError::getDefaultMessage // Mensaje de error
                    ));
            return ResponseEntity.badRequest().body(errors); // Devolver errores de validación
        }

        try {
            // Intentar crear el usuario
            UserEntity newUser = userService.createUser(userDTO);
            return ResponseEntity.ok(Map.of(
                    "message", "Usuario creado exitosamente.",
                    "userId", newUser.getId()
            ));
        } catch (DuplicateUserException ex) {
            // Si se lanza DuplicateUserException, devuelve un error específico
            return ResponseEntity.badRequest().body(Map.of(
                    "error", ex.getMessage()
            ));
        } catch (Exception ex) {
            // Si se lanza cualquier otra excepción, devolver error genérico
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Ocurrió un error inesperado: " + ex.getMessage()
            ));
        }
    }


    // Endpoint para obtener un usuario por su nombre de usuario
    @GetMapping("/{username}")
    public ResponseEntity<UserEntity> getUserByUsername(@PathVariable String username) {
        Optional<UserEntity> user = userService.findUserByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para deshabilitar un usuario (eliminación lógica)
    @PatchMapping("/disable/{id}")
    public ResponseEntity<Void> disableUser(@PathVariable Long id) {
        userService.disableUser(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para actualizar la información del usuario
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity userEntity) {
        userEntity.setId(id);
        UserEntity updatedUser = userService.updateUser(userEntity);
        return ResponseEntity.ok(updatedUser);
    }

}


