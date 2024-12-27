package com.gestioncalendarios.app.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shift")
public class ShiftEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private BusinessEntity businessEntity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;


    @Enumerated(EnumType.STRING)
    private EstadoEnum estado;

    @Column(nullable = false)
    private LocalDate dia;

    @Column(name = "hora_entrada1", nullable = false)
    private LocalTime horaEntrada1;
    @Column(name = "hora_salida1", nullable = false)
    private LocalTime horaSalida1;
    @Column(name = "hora_entrada2", nullable = true)
    private LocalTime horaEntrada2;
    @Column(name = "hora_salida2", nullable = true)
    private LocalTime horaSalida2;

    @Column(name = "hora_real_entrada1", nullable = true)
    private LocalTime horaRealEntrada1;
    @Column(name = "motivo_cambio_hora_entrada1", nullable = true)
    private String MotivoEntrada1;

    @Column(name = "hora_real_salida1", nullable = true)
    private LocalTime horaRealSalida1;
    @Column(name = "motivo_cambio_hora_salida1", nullable = true)
    private String MotivoSalida1;

    @Column(name = "hora_real_entrada2", nullable = true)
    private LocalTime horaRealEntrada2;
    @Column(name = "motivo_cambio_hora_entrada2", nullable = true)
    private String MotivoEntrada2;

    @Column(name = "hora_real_salida2", nullable = true)
    private LocalTime horaRealSalida2;
    @Column(name = "motivo_cambio_hora_salida2", nullable = true)
    private String MotivoSalida2;

    private boolean isPublicado;


}
