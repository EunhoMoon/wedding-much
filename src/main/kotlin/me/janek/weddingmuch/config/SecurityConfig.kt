package me.janek.weddingmuch.config

import me.janek.weddingmuch.utils.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
  private val passwordEncoder: BCryptPasswordEncoder,
  private val jwtProvider: JwtProvider
) {

  @Bean
  protected fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http.csrf { it.disable() }
    http.authorizeHttpRequests {
      it.anyRequest().permitAll()
    }
    http.addFilterBefore(JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter::class.java)
    http.sessionManagement { it.sessionCreationPolicy(STATELESS) }

    return http.build()
  }

}