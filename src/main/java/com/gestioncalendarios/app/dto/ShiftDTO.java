package com.gestioncalendarios.app.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDTO {
    private Long businessId; // ID del negocio
    private List<ShiftWorkerDTO> shifts; // Lista de turnos por trabajador

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShiftWorkerDTO {
        private Long workerId; // ID del trabajador
        private List<ShiftDayDTO> days; // Lista de días y turnos

        @Getter
        @Setter
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class ShiftDayDTO {
            private String estado;
            private LocalDate date; // Fecha del turno
            private LocalTime horaEntrada1;
            private LocalTime horaSalida1;
            private LocalTime horaEntrada2;
            private LocalTime horaSalida2;
            private boolean isPublicado; // Indica si el turno está publicado
        }
    }
}
