package com.gestioncalendarios.app.controller;

import com.gestioncalendarios.app.dto.BusinessDTO;
import com.gestioncalendarios.app.dto.UserDTO;
import com.gestioncalendarios.app.persistence.entity.BusinessEntity;
import com.gestioncalendarios.app.persistence.entity.UserEntity;
import com.gestioncalendarios.app.persistence.repository.UserRepository;
import com.gestioncalendarios.app.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin-dashboard")
public class JefeController {

    @Autowired
    private BusinessService businessService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String dashboard(Model model, Principal principal) {
        // Obtiene el nombre de usuario desde el objeto Principal
        String username = principal.getName();

        // Busca el usuario en la base de datos
        Optional<UserEntity> userOptional = userRepository.findUserEntityByUsername(username);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            // Añade el nombre del usuario al modelo
            model.addAttribute("activeUserName", user.getNombre()); // Aquí pasas el nombre
        } else {
            model.addAttribute("activeUserName", "Usuario desconocido");
        }

        // Supongamos que tienes un servicio de negocio
        List<BusinessEntity> businesses = businessService.findAllVisibleBusinesses();

        model.addAttribute("businesses", businesses);

        // Añade el título al modelo
        model.addAttribute("title", "Panel de Administración");
        return "admin-base"; // Renderiza la plantilla admin-base.html
    }


    @GetMapping("/business/new")
    public String showFormularioNegocio(Model model) {
        model.addAttribute("title", "Nuevo Negocio");
        List<BusinessEntity> businesses = businessService.findAllVisibleBusinesses();
        model.addAttribute("businesses", businesses);
        model.addAttribute("businessDTO", new BusinessDTO());
        return "newBusiness";
    }

    @GetMapping("/user/new")
    public String showFormularioUser(Model model) {
        model.addAttribute("title", "Nuevo Usuario");
        List<BusinessEntity> businesses = businessService.findAllVisibleBusinesses();
        model.addAttribute("businesses", businesses);
        model.addAttribute("userDTO", new UserDTO());
        return "newUser";
    }

    @GetMapping("/business/{businessId}/new-staff")
    public String showAssignStaffPage(@PathVariable Long businessId, Model model) {
        BusinessEntity business = businessService.findById(businessId);

        // Obtener todos los usuarios menos "admin" y los ya asignados al negocio
        List availableUsers = userRepository.findAll();
        availableUsers.removeIf(user -> business.getUsers().contains(user));
        List<BusinessEntity> businesses = businessService.findAllVisibleBusinesses();

        model.addAttribute("businesses", businesses);
        model.addAttribute("business", business);
        model.addAttribute("availableUsers", availableUsers); // Usuarios disponibles
        model.addAttribute("assignedUsers", business.getUsers()); // Usuarios asignados

        return "new-staff";
    }

    @GetMapping("/business/{businessId}/new-horarios")
    public String mostrarHorarios(@PathVariable("businessId") Long businessId, Model model) {
        // Agrega la ID del negocio al modelo para usarlo en el template
        model.addAttribute("businessId", businessId);
        // Agregar enlaces sidebar
        List<BusinessEntity> businesses = businessService.findAllVisibleBusinesses();
        model.addAttribute("businesses", businesses);
        // Establece un título dinámico para la página
        model.addAttribute("title", "Gestión de Horarios - Negocio " + businessId);

        // Retorna el nombre del template a renderizar
        return "new-horarios";
    }
}







