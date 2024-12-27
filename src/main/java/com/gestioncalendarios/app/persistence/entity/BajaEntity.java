package com.gestioncalendarios.app.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bajas")
public class BajaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_inicio_baja", nullable = false)
    private LocalDate fechaInicioBaja;

    private String motivo;

    @Column(name = "fecha_alta", nullable = true)
    private LocalDate fechaAlta;

    // Relación con UserEntity: Una baja pertenece a un usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Esta columna estará en la tabla 'bajas'
    private UserEntity userEntity;


}
