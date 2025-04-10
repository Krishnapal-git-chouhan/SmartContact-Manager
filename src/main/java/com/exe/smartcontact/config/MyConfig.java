package com.exe.smartcontact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity
public class MyConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl(); // Ensure this is properly implemented
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(authenticationProvider()));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN")
                .requestMatchers(new AntPathRequestMatcher("/user/**")).hasRole("USER")
                .anyRequest().permitAll()
            )
            .formLogin(login -> login
//                .loginPage("/login")  // Custom login page
            		.loginPage("/login")
                .loginProcessingUrl("/perform-login") // Processing URL
                .defaultSuccessUrl("/user/index", true) // Redirect after login
                .failureUrl("/login-fail")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .rememberMe(rememberMe -> rememberMe
                .key("uniqueAndSecret") // Unique key for hashing
                .tokenValiditySeconds(86400) // 1 day
                .userDetailsService(userDetailsService())
            )
            .csrf(csrf -> csrf.disable()); // Disable CSRF if needed (only if using APIs)

        return http.build();
    }
}