//package com.gestioncalendarios.app.service;
//
//import com.gestioncalendarios.app.persistence.entity.PermissionEntity;
//import com.gestioncalendarios.app.persistence.entity.StaffEntity;
//import com.gestioncalendarios.app.persistence.entity.UserEntity;
//import com.gestioncalendarios.app.persistence.repository.PermissionRepository;
//import com.gestioncalendarios.app.persistence.repository.StaffRepository;
//import com.gestioncalendarios.app.persistence.repository.UserRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Set;
//
//@Service
//@RequiredArgsConstructor
//public class StaffService {
//
//    private final StaffRepository staffRepository;
//    private final UserRepository userRepository;
//    private final PermissionRepository permissionRepository;
//
//    @Transactional
//    public void addUsersToStaff(Long staffId, List<Long> userIds) {
//        // Buscar la plantilla asociada al staff
//        StaffEntity staff = staffRepository.findById(staffId)
//                .orElseThrow(() -> new RuntimeException("Plantilla no encontrada"));
//
//        // Obtener o crear el permiso del negocio
//        PermissionEntity permission = getOrCreatePermission(staff);
//
//        // Obtener los usuarios y asignarles el permiso
//        Set<UserEntity> users = staff.getUserList(); // Usuarios existentes en el staff
//        userIds.forEach(userId -> {
//            UserEntity user = userRepository.findById(userId)
//                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
//
//            // Asignar el permiso si no lo tiene
//            if (!user.getPermissions().contains(permission)) {
//                user.getPermissions().add(permission);
//            }
//            users.add(user); // Añadir usuario al staff
//        });
//
//        // Guardar todos los usuarios actualizados de una sola vez
//        userRepository.saveAll(users);
//
//        // Actualizar y guardar el staff con los nuevos usuarios
//        staff.setUserList(users);
//        staffRepository.save(staff);
//    }
//
//    private PermissionEntity getOrCreatePermission(StaffEntity staff) {
//        String businessName = staff.getName().replace("Plantilla ", ""); // Obtener nombre del negocio
//        return permissionRepository.findByName(businessName)
//                .orElseGet(() -> permissionRepository.save(
//                        PermissionEntity.builder()
//                                .name(businessName)
//                                .build()
//                ));
//    }
//}






