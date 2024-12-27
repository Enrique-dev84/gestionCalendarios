package com.gestioncalendarios.app.service;

import com.gestioncalendarios.app.dto.ShiftDTO;

import java.util.List;

public interface ShiftService {
    void assignShift(ShiftDTO shiftDTO); // Asignar turnos a trabajadores
    List<ShiftDTO> getShiftsByBusiness(Long businessId); // Obtener turnos por negocio
    List<ShiftDTO> getShiftsByWorker(Long workerId); // Obtener turnos por trabajador
}

