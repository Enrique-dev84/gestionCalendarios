package com.gestioncalendarios.app.persistence.repository;

import com.gestioncalendarios.app.persistence.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusinessRepository extends JpaRepository<BusinessEntity, Long> {

    public List<BusinessEntity> findByName(String name);

    boolean existsByName(String name);

    List<BusinessEntity> findByIsVisibleTrue();


}
