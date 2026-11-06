package edu.pucp.gtics.lab8.config;

// PASO 6 (ver application.properties): toda la seguridad. Busca abajo 6a, 6b, 6c, 6d y 6e.

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/*
 * NOTA: el enunciado (2022) dice "WebSecurityConfigurerAdapter". Esa clase se eliminó
 * en Spring Security 6 / Spring Boot 3. Hoy se hace lo mismo con dos @Bean:
 *   - SecurityFilterChain  -> reemplaza a configure(HttpSecurity http)
 *   - UserDetailsManager   -> reemplaza a configure(AuthenticationManagerBuilder auth)
 * La lógica (URLs, roles, queries) es idéntica.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // ===================== 1) AUTORIZACIÓN + LOGIN + LOGOUT =====================
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // ---- PASO 6a: LOGIN (enunciado 1.1) ----
        http.formLogin(form -> form
                .loginPage("/user/signIn")                    // página (GET) con el formulario
                .loginProcessingUrl("/processLogin")          // POST del formulario: lo procesa Spring, NO un controller
                .defaultSuccessUrl("/user/signInRedirect", true) // a dónde va tras loguearse bien
                .permitAll());

        // ---- PASO 6b: LOGOUT ----
        http.logout(logout -> logout
                .logoutSuccessUrl("/")                        // a dónde va tras cerrar sesión
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true));                // borra la sesión (usuario, carrito, ncarrito)

        // ---- PASO 6c: AUTORIZACIONES (enunciado 2.5.1) (el orden importa: de lo más específico a lo más general) ----
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/plataformas/**", "/distribuidoras/**").hasAuthority("ADMIN")
                .requestMatchers("/juegos/editarFrm", "/juegos/guardar", "/juegos/borrar").hasAuthority("ADMIN")
                .requestMatchers("/juegos/lista").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/carrito/**").hasAuthority("USER")
                .requestMatchers("/user/signInRedirect").authenticated()
                .anyRequest().permitAll());                   // "/", "/vista", "/juegos/vista", "/user/signIn", css...

        return http.build();
    }

    // ===================== 2) AUTENTICACIÓN (¿quién eres?) contra la BD =====================
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        // ---- PASO 6d: las 2 queries que usa Spring para validar el login ----
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        // Debe devolver EXACTAMENTE 3 columnas en este orden: username, password, enabled
        users.setUsersByUsernameQuery(
                "SELECT correo, password, enabled FROM usuarios WHERE correo = ?");

        // Debe devolver 2 columnas: username, rol (authority)
        users.setAuthoritiesByUsernameQuery(
                "SELECT correo, autorizacion FROM usuarios WHERE correo = ? AND enabled = 1");

        return users;
    }

    // ---- PASO 6e: BCrypt ----
    // Las contraseñas en la BD están hasheadas con BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
