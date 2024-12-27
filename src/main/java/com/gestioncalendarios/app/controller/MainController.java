package com.gestioncalendarios.app.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@Controller
public class MainController {

    @GetMapping("/")
    public String root(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            // Si el usuario no está autenticado, redirige al login
            return "redirect:/login";
        }

        // Verifica los roles del usuario y redirige a la página correspondiente
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_JEFE"))) {
            return "redirect:/admin-dashboard";
        } else if (authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_ENCARGADO_NEGOCIO"))) {
            return "redirect:/negocio-dashboard";
        } else if (authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_ENCARGADO_TURNO"))) {
            return "redirect:/turno-dashboard";
        } else if (authorities.stream().anyMatch(role -> role.getAuthority().equals("ROLE_TRABAJADOR"))) {
            return "redirect:/trabajador-dashboard";
        }

        // Si no tiene un rol válido, redirige al login con un error
        return "redirect:/login?error=true";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login"; // Retorna la vista del login (login.html)
    }
}
