package com.gestioncalendarios.app.config;

import com.gestioncalendarios.app.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Bean AuthenticationManager para la configuración de la autenticación
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManager.class);
    }

    // Bean AuthenticationProvider para la autenticación del usuario
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Bean PasswordEncoder para la codificación de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Codificación segura de contraseñas
    }

    // Configuración de la seguridad HTTP para diferentes roles
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/dist/**", "/plugins/**").permitAll() // Permitir acceso a login y recursos estáticos
                        .requestMatchers("/admin-dashboard/**").hasRole("JEFE")
                        .requestMatchers("/negocio-dashboard/**").hasRole("ENCARGADO_NEGOCIO")
                        .requestMatchers("/turno-dashboard/**").hasRole("ENCARGADO_TURNO")
                        .requestMatchers("/trabajador-dashboard/**").hasRole("TRABAJADOR")
                        .anyRequest().authenticated() // Requiere autenticación para otras rutas
                )
                .formLogin(form -> form
                        .loginPage("/login") // Página personalizada para el login
                        .permitAll() // Permitir acceso a todos a la página de login
                        .defaultSuccessUrl("/", true) // Redirige siempre a la raíz si no se especifica lo contrario
                        .successHandler((request, response, authentication) -> {
                            String role = authentication.getAuthorities().toString();
                            if (role.contains("JEFE")) {
                                response.sendRedirect("/admin-dashboard"); // Redirige al panel del jefe
                            } else if (role.contains("ENCARGADO_NEGOCIO")) {
                                response.sendRedirect("/negocio-dashboard"); // Redirige al panel del encargado de negocio
                            } else if (role.contains("ENCARGADO_TURNO")) {
                                response.sendRedirect("/turno-dashboard"); // Redirige al panel del encargado de turno
                            } else if (role.contains("TRABAJADOR")) {
                                response.sendRedirect("/trabajador-dashboard"); // Redirige al panel del trabajador
                            } else {
                                response.sendRedirect("/"); // Redirige a la raíz si no tiene un rol definido
                            }
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}




