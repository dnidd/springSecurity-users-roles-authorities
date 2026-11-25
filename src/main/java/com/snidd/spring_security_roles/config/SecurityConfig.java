package com.snidd.spring_security_roles.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configure security filter chain.
 * Password encoder.
 */
@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authReq -> {
                    authReq
                            // All endpoint private, except public "register".
                            .requestMatchers("/api/user/register").permitAll()
                            .requestMatchers("/admin").hasRole("ADMIN")
                            // If an endpoint requires both role AND authority.
                            // (defined in UserService, loadUserByUsername())
                            .requestMatchers("/api/greet/update").hasAuthority("USER_WRITE_PERM")
                            // If an endpoint requires authority only.
//                            .requestMatchers("/api/greet/update").hasAuthority("WRITE_PERM")
                            .anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }


    /**
     * Decoding/hashing password when "register" in UserService.
     * Also used to match the authentication.
     * @return BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
