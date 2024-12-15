package com.gestioncalendarios.app.controller;

import com.gestioncalendarios.app.persistence.entity.BusinessEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/negocios")
public class NegocioController {

    // Mostrar el formulario para crear un nuevo negocio
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("negocio", new BusinessEntity()); // Se crea un objeto vacío para el formulario
        return "negocios/nuevo-negocio"; // Nombre del archivo HTML del formulario
    }
}

