package com.gestioncalendarios.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/negocio-dashboard")
public class EncargadoNegocioController {

    @GetMapping
    public String showDashboard(Model model) {
        // Lógica para mostrar datos al Encargado de Negocio
        model.addAttribute("message", "Bienvenido, Encargado de Negocio!");
        return "negocio-dashboard"; // Nombre de la vista Thymeleaf
    }
}
