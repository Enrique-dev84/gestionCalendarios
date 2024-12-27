package com.gestioncalendarios.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/turno-dashboard")
public class EncargadoTurnoController {

    @GetMapping
    public String showDashboard(Model model) {
        // Lógica para mostrar datos al Encargado de Turno
        model.addAttribute("message", "Bienvenido, Encargado de Turno!");
        return "turno-dashboard"; // Nombre de la vista Thymeleaf
    }
}
