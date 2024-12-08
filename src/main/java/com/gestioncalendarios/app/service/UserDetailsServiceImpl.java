package com.gestioncalendarios.app.service;

import com.gestioncalendarios.app.persistence.entity.UserEntity;
import com.gestioncalendarios.app.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("El usuario " + username +" no existe"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRolList()
                .forEach(rol -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getRolEnum().name()))));

        userEntity.getRolList().stream()
                .flatMap(rol->rol.getPermissionList().stream())
                .forEach(permission->authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);

    }
}
