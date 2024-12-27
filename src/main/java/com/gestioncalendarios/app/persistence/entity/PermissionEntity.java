package com.gestioncalendarios.app.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permission")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String name;

    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore // Evitar ciclos infinitos en serialización
    @Builder.Default
    private Set<UserEntity> users = new HashSet<>();
}

