package com.gestioncalendarios.app.dto;

import com.gestioncalendarios.app.persistence.entity.UserEntity;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BusinessDTO {

    @NotNull(message = "El nombre del negocio no puede estar vacío.")
    private String name;

    @NotNull(message = "La fecha de inicio es obligatoria.")
    private LocalDate fechaInicio;

    private LocalDate fechaFinalizacion;

    @NotNull(message = "La hora de apertura es obligatoria.")
    private LocalTime horaApertura;

    @NotNull(message = "La hora de cierre es obligatoria.")
    private LocalTime horaCierre;

    @AssertTrue(message = "La fecha de finalización debe ser igual o posterior a la fecha de inicio.")
    public boolean isFechaFinalizacionValida() {
        return fechaFinalizacion == null || !fechaFinalizacion.isBefore(fechaInicio);
    }

    private List<UserEntity> staff = new ArrayList<>();
}

