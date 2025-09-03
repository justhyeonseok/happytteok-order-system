package com.gk.happytteokordersystem.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.withUsername("admin")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(user)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authz ->
                authz
                    // Swagger UI와 API docs에 대한 접근을 보호
                    .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").authenticated()
                    // 다른 모든 API 엔드포인트는 인증 없이 접근 허용
                    .anyRequest().permitAll()
            }
            .httpBasic { it.init(http) }
            .csrf { it.disable() } // CSRF 비활성화 (API 서버이므로 필요 없음)

        return http.build()
    }
}