package me.janek.weddingmuch.utils

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import me.janek.weddingmuch.api.user.UserDetailsResponse
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.time.Duration
import kotlin.time.toJavaDuration

@Component
class JwtProvider(
  private val jwtProperties: JwtProperties
) {

  private val SECRET_KEY = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8))

  fun generateToken(user: UserDetailsResponse, expiredAt: Duration): String {
    val now = Date()
    return createToken(Date(now.time + expiredAt.toJavaDuration().toMillis()), user)
  }

  private fun createToken(expiry: Date, user: UserDetailsResponse): String {
    val now = Date()

    return Jwts.builder()
      .issuer(jwtProperties.issuer)
      .issuedAt(now)
      .expiration(expiry)
      .subject(user.username)
      .claim("userToken", user.token)
      .signWith(SECRET_KEY, Jwts.SIG.HS512)
      .compact()
  }

  fun verifyToken(jwtToken: String): String {
    val claimsJws = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(jwtToken)

    return claimsJws.payload.subject
  }

}