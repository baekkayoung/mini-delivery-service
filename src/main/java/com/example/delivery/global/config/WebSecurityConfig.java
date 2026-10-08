package com.example.delivery.global.config;

import com.example.delivery.jwt.JwtAuthorizationFilter;
import com.example.delivery.jwt.JwtUtil;
import com.example.delivery.security.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.access.AccessDeniedHandler;

@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/menus").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/menus/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/menus").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/menus/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/menus/**").hasRole("OWNER")
                        .requestMatchers(HttpMethod.PATCH, "/api/orders/*/status").hasRole("OWNER")

                        .requestMatchers(HttpMethod.POST,"/api/orders").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE,"/api/orders/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/orders/*/payments").hasRole("CUSTOMER")

                        .anyRequest().permitAll()
                )

                .exceptionHandling(exception -> exception
                        // 인증되지 않은 경우 → 401
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write(
                                    "{\"status\":401,\"message\":\"JWT 토큰이 없습니다.\"}"
                            );
                        })

                        // 인증은 됐지만 권한이 없는 경우 → 403
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write(
                                    "{\"status\":403,\"message\":\"해당 요청에 대한 권한이 없습니다.\"}"
                            );
                        })
                )


                .csrf(csrf -> csrf.disable())
                .addFilterBefore(
                        jwtAuthorizationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }




}
