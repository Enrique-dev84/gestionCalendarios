package com.gestioncalendarios.app.service;

import com.gestioncalendarios.app.persistence.entity.UserEntity;
import com.gestioncalendarios.app.persistence.repository.UserRepository;
import com.gestioncalendarios.app.config.CustomUserDetails; // Asegúrate de importar la clase CustomUserDetails
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Buscar al usuario en la base de datos
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        // Concatenar roles y permisos en una sola lista de autoridades
        List<SimpleGrantedAuthority> authorities = userEntity.getRolList().stream()
                .flatMap(rol -> {
                    // Convertir los roles a autoridades
                    List<SimpleGrantedAuthority> roleAuthorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol.getRolEnum().name()));

                    // Convertir los permisos a autoridades
                    List<SimpleGrantedAuthority> permissionAuthorities = rol.getPermissionList().stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                            .toList();

                    // Combinar ambos listados en un solo flujo
                    return Stream.concat(roleAuthorities.stream(), permissionAuthorities.stream());
                })
                .collect(Collectors.toList());

        // Devolver un CustomUserDetails que incluye el nombre
        return new CustomUserDetails(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorities,
                userEntity.getNombre()); // Pasar el nombre aquí
    }
}

