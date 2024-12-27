package com.gestioncalendarios.app.controller;

import com.gestioncalendarios.app.dto.BusinessDTO;
import com.gestioncalendarios.app.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin-dashboard/business")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    @PostMapping("/new")
    public ResponseEntity<Map<String, Object>> crearNegocio(@Valid @RequestBody BusinessDTO businessDTO) {
        Map<String, Object> response = new HashMap<>();
        var business = businessService.createBusiness(businessDTO);
        response.put("businessId", business.getId());
        response.put("message", "Negocio creado exitosamente.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{businessId}/update-users")
    public ResponseEntity<Map<String, Object>> updateUsersInBusiness(
            @PathVariable Long businessId,
            @RequestBody Map<String, List<Long>> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Long> usuariosAsignados = body.get("usuariosAsignados");
            List<Long> usuariosEliminados = body.get("usuariosEliminados");

            if (usuariosAsignados != null && !usuariosAsignados.isEmpty()) {
                businessService.assignUsersToBusiness(businessId, usuariosAsignados);
            }

            if (usuariosEliminados != null && !usuariosEliminados.isEmpty()) {
                businessService.removeUsersFromBusiness(businessId, usuariosEliminados);
            }

            response.put("message", "Usuarios actualizados exitosamente.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Error al actualizar usuarios: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/{businessId}/remove-users")
    public ResponseEntity<Map<String, Object>> removeUsersFromBusiness(@PathVariable Long businessId,
                                                                       @RequestParam List<Long> userIds) {
        Map<String, Object> response = new HashMap<>();
        businessService.removeUsersFromBusiness(businessId, userIds);
        response.put("message", "Usuarios eliminados exitosamente del negocio.");
        return ResponseEntity.ok(response);
    }
}






