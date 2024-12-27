package com.gestioncalendarios.app.service.impl;

import com.gestioncalendarios.app.dto.ShiftDTO;
import com.gestioncalendarios.app.persistence.entity.ShiftEntity;
import com.gestioncalendarios.app.persistence.entity.EstadoEnum;
import com.gestioncalendarios.app.persistence.repository.ShiftRepository;
import com.gestioncalendarios.app.persistence.repository.UserRepository;
import com.gestioncalendarios.app.mapper.ShiftMapper;
import com.gestioncalendarios.app.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftServiceImpl implements ShiftService {

    private final ShiftRepository shiftRepository;
    private final UserRepository userRepository;
    private final ShiftMapper shiftMapper;

    @Autowired
    public ShiftServiceImpl(ShiftRepository shiftRepository, UserRepository userRepository, ShiftMapper shiftMapper) {
        this.shiftRepository = shiftRepository;
        this.userRepository = userRepository;
        this.shiftMapper = shiftMapper;
    }

    @Override
    public void assignShift(ShiftDTO shiftDTO) {
        shiftDTO.getShifts().forEach(shiftWorkerDTO -> {
            Long workerId = shiftWorkerDTO.getWorkerId();

            // Verificar si el trabajador está de baja
            shiftWorkerDTO.getDays().forEach(dayDTO -> {
                boolean isBaja = EstadoEnum.BAJA.name().equalsIgnoreCase(dayDTO.getEstado());
                if (isBaja) {
                    System.out.println("El trabajador " + workerId + " todavía no ha recibido el alta.");
                }

                // Validar que no tenga dos turnos al mismo tiempo en diferentes negocios
                boolean hasConflict = shiftRepository.existsConflictingShift(
                        workerId,
                        dayDTO.getDate(),
                        dayDTO.getHoraEntrada1(),
                        dayDTO.getHoraSalida1()
                );
                if (hasConflict) {
                    throw new IllegalStateException("Conflicto: el trabajador " + workerId + " ya tiene un turno asignado en otro negocio.");
                }

                // Guardar el turno en la base de datos
                ShiftEntity shiftEntity = shiftMapper.dtoToEntity(dayDTO, null, null); // Asocia businessEntity y userEntity
                shiftRepository.save(shiftEntity);
            });
        });
    }

    @Override
    public List<ShiftDTO> getShiftsByBusiness(Long businessId) {
        // Implementar lógica para obtener turnos por negocio
        return null;
    }

    @Override
    public List<ShiftDTO> getShiftsByWorker(Long workerId) {
        // Implementar lógica para obtener turnos por trabajador
        return null;
    }
}


