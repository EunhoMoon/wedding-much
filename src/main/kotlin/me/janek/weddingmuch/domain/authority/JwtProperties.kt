package me.janek.weddingmuch.domain.authority

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("jwt")
class JwtProperties(
  var issuer: String = "",
  var secretKey: String = "",
)