package ru.oleynik.otus.backend.app.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    private val authFilter: AuthFilter,
) {

    @Bean
    fun securityChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors {
                it.configurationSource {
                    CorsConfiguration().also { cors ->
                        cors.allowedOriginPatterns = listOf("*")
                        cors.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        cors.allowedHeaders = listOf("*")
                        cors.allowCredentials = true
                    }
                }
            }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/health").permitAll()
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    @Primary
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}