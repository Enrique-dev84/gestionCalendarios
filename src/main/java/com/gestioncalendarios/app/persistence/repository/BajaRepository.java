package com.gestioncalendarios.app.persistence.repository;

import com.gestioncalendarios.app.persistence.entity.BajaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BajaRepository extends JpaRepository<BajaEntity, Long> {
}
