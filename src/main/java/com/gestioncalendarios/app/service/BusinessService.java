package com.gestioncalendarios.app.service;

import com.gestioncalendarios.app.dto.BusinessDTO;
import com.gestioncalendarios.app.exception.BusinessException;
import com.gestioncalendarios.app.persistence.entity.BusinessEntity;
import com.gestioncalendarios.app.persistence.entity.PermissionEntity;
import com.gestioncalendarios.app.persistence.entity.UserEntity;
import com.gestioncalendarios.app.persistence.repository.BusinessRepository;
import com.gestioncalendarios.app.persistence.repository.PermissionRepository;
import com.gestioncalendarios.app.persistence.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    /**
     * Crear un nuevo negocio y su permiso asociado
     */
    @Transactional
    public BusinessEntity createBusiness(BusinessDTO businessDTO) {
        // Validar si el nombre del negocio ya existe
        if (businessRepository.existsByName(businessDTO.getName())) {
            throw new BusinessException("El nombre del negocio ya está en uso: " + businessDTO.getName());
        }

        // Validar fechas y horas
        validateBusinessDatesAndTimes(
                businessDTO.getFechaInicio(),
                businessDTO.getFechaFinalizacion(),
                businessDTO.getHoraApertura(),
                businessDTO.getHoraCierre()
        );

        // Crear y guardar el negocio
        BusinessEntity business = BusinessEntity.builder()
                .name(businessDTO.getName())
                .fechaInicio(businessDTO.getFechaInicio())
                .fechaFinalizacion(businessDTO.getFechaFinalizacion())
                .horaApertura(businessDTO.getHoraApertura())
                .horaCierre(businessDTO.getHoraCierre())
                .isVisible(true)
                .build();

        businessRepository.save(business);

        // Crear el permiso asociado al negocio
        createPermissionIfNotExists(businessDTO.getName());

        return business;
    }

    /**
     * Validar fechas y horas
     */
    private void validateBusinessDatesAndTimes(LocalDate fechaInicio, LocalDate fechaFinalizacion,
                                               LocalTime horaApertura, LocalTime horaCierre) {
        if (fechaInicio == null) {
            throw new BusinessException("La fecha de inicio es obligatoria.");
        }
        if (fechaFinalizacion != null && fechaInicio.isAfter(fechaFinalizacion)) {
            throw new BusinessException("La fecha de inicio no puede ser después de la fecha de finalización.");
        }

    }

    /**
     * Crear un permiso si no existe
     */
    private void createPermissionIfNotExists(String permissionName) {
        permissionRepository.findByName(permissionName).ifPresentOrElse(
                permission -> { /* El permiso ya existe, no es necesario crearlo */ },
                () -> {
                    PermissionEntity permission = PermissionEntity.builder()
                            .name(permissionName)
                            .build();
                    permissionRepository.save(permission);
                }
        );
    }

    /**
     * Asignar usuarios a un negocio y otorgarles el permiso correspondiente
     */
    @Transactional
    public void assignUsersToBusiness(Long businessId, List<Long> userIds) {
        BusinessEntity business = findById(businessId);

        // Obtener el permiso asociado al negocio (si aplica)
        PermissionEntity businessPermission = permissionRepository.findByName(business.getName())
                .orElseThrow(() -> new BusinessException("Permiso no encontrado para el negocio: " + business.getName()));

        // Obtener los usuarios seleccionados por sus IDs
        List<UserEntity> selectedUsers = userRepository.findAllById(userIds);

        // Validar que todos los usuarios existan
        if (selectedUsers.size() != userIds.size()) {
            throw new BusinessException("Uno o más usuarios no existen.");
        }

        // Asignar usuarios al negocio y otorgar permisos
        selectedUsers.forEach(user -> {
            if (!business.getUsers().contains(user)) {
                business.getUsers().add(user);
                user.getPermissions().add(businessPermission);
                userRepository.save(user); // Guarda los cambios en el usuario
            }
        });

        // Guardar cambios en el negocio
        businessRepository.save(business);
    }

    /**
     * Remover usuarios de un negocio y revocarles el permiso correspondiente
     */
    @Transactional
    public void removeUsersFromBusiness(Long businessId, List<Long> userIds) {
        // Buscar el negocio
        BusinessEntity business = findById(businessId);

        // Obtener el permiso asociado al negocio
        PermissionEntity businessPermission = permissionRepository.findByName(business.getName())
                .orElseThrow(() -> new BusinessException("Permiso no encontrado para el negocio: " + business.getName()));

        // Obtener los usuarios por sus IDs
        List<UserEntity> selectedUsers = userRepository.findAllById(userIds);

        // Validar que todos los usuarios existan
        if (selectedUsers.size() != userIds.size()) {
            throw new BusinessException("Uno o más usuarios no existen.");
        }

        // Remover usuarios del negocio y revocarles el permiso
        selectedUsers.forEach(user -> {
            if (business.getUsers().contains(user)) {
                business.getUsers().remove(user);
                user.getPermissions().remove(businessPermission);
                userRepository.save(user);
            }
        });

        // Guardar cambios en el negocio
        businessRepository.save(business);
    }

    /**
     * Buscar negocio por ID
     */
    public BusinessEntity findById(Long businessId) {
        return businessRepository.findById(businessId)
                .orElseThrow(() -> new BusinessException("Negocio no encontrado con ID: " + businessId));
    }

    /**
     * Listar negocios visibles
     */
    public List<BusinessEntity> findAllVisibleBusinesses() {
        return businessRepository.findByIsVisibleTrue();  // Devuelve los negocios con isVisible = true
    }
}














