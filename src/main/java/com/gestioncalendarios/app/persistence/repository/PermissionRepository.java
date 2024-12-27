package com.gestioncalendarios.app.persistence.repository;

import com.gestioncalendarios.app.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    Optional<PermissionEntity> findByName(String name);


    boolean existsByName(String name);
}
