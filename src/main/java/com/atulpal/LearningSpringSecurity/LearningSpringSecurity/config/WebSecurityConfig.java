package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.config;

import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/posts", "/error", "/auth/**" ).permitAll() // posts route is enabled for everyone
//                        .requestMatchers("/posts/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authorizationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //It's only used for testing purposes in development phase in production ready code it is not usable
//    @Bean
//    UserDetailsService myInMemoryUserDetailsService() {
//        UserDetails adminUser = User
//                .withUsername("atul")
//                .password(passwordEncoder().encode("atul6396"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user1 = User
//                .withUsername("Anuj")
//                .password(passwordEncoder().encode("anuj6396"))
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User
//                .withUsername("Prajakta")
//                    .password(passwordEncoder().encode("prajakta6396"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(adminUser, user1, user2);
//    }


}
