package com.gestioncalendarios.app.persistence.repository;

import com.gestioncalendarios.app.persistence.entity.VacacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacacionesRepository extends JpaRepository<VacacionesEntity, Long> {
}
