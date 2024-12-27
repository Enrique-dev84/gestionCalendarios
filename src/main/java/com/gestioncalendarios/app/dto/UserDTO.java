package com.gestioncalendarios.app.dto;

import com.gestioncalendarios.app.persistence.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio.")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String password;
    private String nombre;

    @Email(message = "El correo electrónico debe ser válido.")
    private String email;
    private String phoneNumber;
    private boolean recibirNotificacionesTurnos;

}
