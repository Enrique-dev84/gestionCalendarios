package com.gestioncalendarios.app.persistence.repository;

import com.gestioncalendarios.app.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);

//    @Query("SELECT u FROM UserEntity u JOIN StaffEntity s ON s MEMBER OF u.staffList WHERE s.businessEntity.id = :businessId")
//    List<UserEntity> findUsersInBusiness(@Param("businessId") Long businessId);

    @Query(value = "SELECT u.* FROM user u WHERE u.username != 'admin' AND NOT EXISTS (" +
            "SELECT 1 FROM staff_user su JOIN staff s ON su.staff_id = s.id " +
            "WHERE s.id = id AND su.user_id = u.id)", nativeQuery = true)
    List<UserEntity> findUsersNotInBusiness(@Param("businessId") Long businessId);




}

