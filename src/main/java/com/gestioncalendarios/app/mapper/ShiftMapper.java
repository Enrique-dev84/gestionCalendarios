package com.gestioncalendarios.app.mapper;

import com.gestioncalendarios.app.dto.ShiftDTO;
import com.gestioncalendarios.app.persistence.entity.BusinessEntity;
import com.gestioncalendarios.app.persistence.entity.EstadoEnum;
import com.gestioncalendarios.app.persistence.entity.ShiftEntity;
import com.gestioncalendarios.app.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ShiftMapper {

    // Mapea ShiftEntity -> ShiftDTO
    public ShiftDTO.ShiftWorkerDTO.ShiftDayDTO entityToDTO(ShiftEntity entity) {
        return ShiftDTO.ShiftWorkerDTO.ShiftDayDTO.builder()
                .estado(entity.getEstado().name())
                .date(entity.getDia())
                .horaEntrada1(entity.getHoraEntrada1())
                .horaSalida1(entity.getHoraSalida1())
                .horaEntrada2(entity.getHoraEntrada2())
                .horaSalida2(entity.getHoraSalida2())
                .isPublicado(entity.isPublicado())
                .build();
    }

    // Mapea ShiftDTO -> ShiftEntity
    public ShiftEntity dtoToEntity(ShiftDTO.ShiftWorkerDTO.ShiftDayDTO dto, BusinessEntity businessEntity, UserEntity userEntity) {
        return ShiftEntity.builder()
                .estado(EstadoEnum.valueOf(dto.getEstado().toUpperCase()))
                .dia(dto.getDate())
                .horaEntrada1(dto.getHoraEntrada1())
                .horaSalida1(dto.getHoraSalida1())
                .horaEntrada2(dto.getHoraEntrada2())
                .horaSalida2(dto.getHoraSalida2())
                .isPublicado(dto.isPublicado())
                .businessEntity(businessEntity)
                .userEntity(userEntity)
                .build();
    }
}

