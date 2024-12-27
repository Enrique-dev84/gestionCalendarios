package com.gestioncalendarios.app.service;

import com.gestioncalendarios.app.persistence.entity.RolEntity;
import com.gestioncalendarios.app.persistence.entity.RolEnum;
import com.gestioncalendarios.app.persistence.repository.RolRepository;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public void createRoles() {
        if (!rolRepository.existsByRolEnum(RolEnum.JEFE)) {
            RolEntity jefeRol = RolEntity.builder()
                    .rolEnum(RolEnum.JEFE)
                    .build();
            rolRepository.save(jefeRol);
        }

        if (!rolRepository.existsByRolEnum(RolEnum.ENCARGADO_NEGOCIO)) {
            RolEntity encargadoNegocioRol = RolEntity.builder()
                    .rolEnum(RolEnum.ENCARGADO_NEGOCIO)
                    .build();
            rolRepository.save(encargadoNegocioRol);
        }

        if (!rolRepository.existsByRolEnum(RolEnum.ENCARGADO_TURNO)) {
            RolEntity encargadoTurnoRol = RolEntity.builder()
                    .rolEnum(RolEnum.ENCARGADO_TURNO)
                    .build();
            rolRepository.save(encargadoTurnoRol);
        }

        if (!rolRepository.existsByRolEnum(RolEnum.TRABAJADOR)) {
            RolEntity trabajadorRol = RolEntity.builder()
                    .rolEnum(RolEnum.TRABAJADOR)
                    .build();
            rolRepository.save(trabajadorRol);
        }
    }
}
