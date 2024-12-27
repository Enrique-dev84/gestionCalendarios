package com.gestioncalendarios.app.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "business")
public class BusinessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    @Column(name = "fecha_finalizacion",nullable = true)
    private LocalDate fechaFinalizacion;

    @Column(name = "hora_apertura")
    private LocalTime horaApertura;
    @Column(name = "hora_cierre")
    private LocalTime horaCierre;

    @Column(name = "is_visible")
    private boolean isVisible;

//    @OneToOne
//    @JoinColumn(name = "staff_id")
//    private StaffEntity staffEntity;

    @ManyToMany
    @JoinTable(
            name = "business_user", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "business_id"), // Clave foránea de BusinessEntity
            inverseJoinColumns = @JoinColumn(name = "user_id") // Clave foránea de UserEntity
    )
    @Builder.Default
    private Set<UserEntity> users = new HashSet<>();



}

