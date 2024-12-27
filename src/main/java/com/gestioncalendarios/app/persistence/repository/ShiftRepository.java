package com.gestioncalendarios.app.persistence.repository;

import com.gestioncalendarios.app.persistence.entity.ShiftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
            "FROM ShiftEntity s " +
            "WHERE s.userEntity.id = :workerId " +
            "AND s.dia = :date " +
            "AND (s.horaEntrada1 < :endTime AND s.horaSalida1 > :startTime)")
    boolean existsConflictingShift(Long workerId, LocalDate date, LocalTime startTime, LocalTime endTime);

}
