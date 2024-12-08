package com.gestioncalendarios.app.persistence.entity;


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
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(name = "is_Enabled")
    private boolean isEnabled;

    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;

    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;

    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<RolEntity> rolList = new HashSet<>();


}
