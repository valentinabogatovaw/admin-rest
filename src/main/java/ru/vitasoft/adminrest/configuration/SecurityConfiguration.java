package ru.vitasoft.adminrest.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {


    @Autowired
    private LoginUserDetailsService loginUserDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests(
//                        (authz) -> authz.anyRequest()
//                                .authenticated())
//
//                .httpBasic(Customizer.withDefaults())
//                .userDetailsService(loginUserDetailsService);
//
//        return http.build();

        http.csrf().disable();
        http.httpBasic(Customizer.withDefaults());
        http.userDetailsService(loginUserDetailsService);
        http.authorizeHttpRequests().antMatchers( "/user/**").hasAnyAuthority("USER");
        http.authorizeHttpRequests().antMatchers("/operator/**").hasAnyAuthority("OPERATOR");
        http.authorizeHttpRequests().antMatchers("/admin/**").hasAnyAuthority("ADMIN");

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}