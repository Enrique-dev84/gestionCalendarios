package com.gestioncalendarios.app.persistence.repository;

import com.gestioncalendarios.app.persistence.entity.RolEntity;
import com.gestioncalendarios.app.persistence.entity.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByRolEnum(RolEnum rolEnum);

    boolean existsByRolEnum(RolEnum rolEnum);
}
