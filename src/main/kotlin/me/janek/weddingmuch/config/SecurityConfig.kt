package me.janek.weddingmuch.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
  private val passwordEncoder: BCryptPasswordEncoder
) {

  @Bean
  protected fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http.csrf { it.disable() }
    http.authorizeHttpRequests {
      it.anyRequest().permitAll()
    }
    http.sessionManagement { it.sessionCreationPolicy(STATELESS) }

    return http.build()
  }

}