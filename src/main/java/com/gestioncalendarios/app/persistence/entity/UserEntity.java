package com.gestioncalendarios.app.persistence.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isEnabled;

    @Column(name = "is_account_No_Expired", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean accountNoExpired;

    @Column(name = "account_No_Locked", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean accountNoLocked;

    @Column(name = "credential_No_Expired", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    @Builder.Default
    private Set<RolEntity> rolList = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_permission", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @Builder.Default
    private Set<PermissionEntity> permissions = new HashSet<>();

    private String nombre;
    private String email;
    private String phoneNumber;
    @Column(name = "notificaciones_turnos")
    private boolean recibirNotificacionesTurnos;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ShiftEntity> shifts = new ArrayList<>();

    @ManyToMany(mappedBy = "users") // Relación bidireccional, usa el atributo 'users' de BusinessEntity
    @Builder.Default
    private Set<BusinessEntity> businesses = new HashSet<>();

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private VacacionesEntity vacaciones;

    // Relación con BajaEntity: Un usuario puede tener varias bajas
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<BajaEntity> bajas = new ArrayList<>();

    // Método para encriptar la contraseña antes de guardarla
    public void setPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

}
