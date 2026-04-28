
package com.BankManagementSystemProject.config;

import com.BankManagementSystemProject.security.CustomUserDetailService;
import com.BankManagementSystemProject.security.JwtAuthenticationEntryPoint;
import com.BankManagementSystemProject.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired; import
        org.springframework.context.annotation.Bean; import
        org.springframework.context.annotation.Configuration; import
        org.springframework.http.HttpMethod; import
        org.springframework.security.authentication.AuthenticationManager; import
        org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import
        org.springframework.security.config.annotation.authentication.configuration.
                AuthenticationConfiguration; import
        org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; import
        org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import
        org.springframework.security.crypto.password.PasswordEncoder; import
        org.springframework.security.web.DefaultSecurityFilterChain; import
        org.springframework.security.web.SecurityFilterChain;


@Configuration public class SecurityConfig {

    @Autowired private CustomUserDetailService customUserDetailService;


    @Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;


    private static final String[] PUBLIC_URLS = {
            "/api/v1/auth/**",
            "/api/users/create",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()   // ✅ public APIs
                        .anyRequest().authenticated()               // ✅ secured APIs
                )
                // .httpBasic();


                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        //Filter Placement

        http.addFilterBefore(jwtAuthenticationFilter,
                org.springframework.security.web.authentication.
                        UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(daoAuthenticationProvider());

        return http.build(); }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder()); return provider; }

    @Bean
    public PasswordEncoder passwordEncoder() { return new
            BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager
    authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    } }

