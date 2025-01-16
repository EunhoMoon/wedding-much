package me.janek.weddingmuch.utils

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import me.janek.weddingmuch.api.user.UserDetailsResponse
import me.janek.weddingmuch.infrastructure.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration

@Component
class JwtProvider(
  private val jwtProperties: JwtProperties,
  private val userRepository: UserRepository
) {

  private val SECRET_KEY = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8))

  private val log = LoggerFactory.getLogger(this::class.java)

  fun generateToken(user: UserDetailsResponse, expiredAt: Duration = 10.toDuration(DurationUnit.MINUTES)): String {
    val now = Date()
    return createToken(Date(now.time + expiredAt.toJavaDuration().toMillis()), user)
  }

  private fun createToken(expiry: Date, user: UserDetailsResponse) =
    Jwts.builder()
      .issuer(jwtProperties.issuer)
      .issuedAt(Date())
      .expiration(expiry)
      .subject(user.username)
      .claim("userToken", user.token)
      .claim("authorities", user.authorities)
      .signWith(SECRET_KEY, Jwts.SIG.HS512)
      .compact()

  fun verifyToken(jwtToken: String): Boolean {
    try {
      getUserTokenFromJwtToken(jwtToken)

      return true
    } catch (e: Exception) {
      when (e) {
        is SecurityException -> log.error("Invalid JWT Token")
        is MalformedJwtException -> log.error("Invalid JWT Token")
        is ExpiredJwtException -> log.error("Expired JWT Token")
        is UnsupportedJwtException -> log.error("Unsupported JWT Token")
        is IllegalArgumentException -> log.error("JWT claims string is empty")
        else -> log.error("Unknown Exception")
      }
      log.error(e.message)
    }
    return false
  }

  fun getAuthentication(jwtToken: String): Authentication =
    getUserTokenFromJwtToken(jwtToken).toString()
      .let { userRepository.findByToken(it) ?: throw IllegalArgumentException("해당하는 사용자를 찾을 수 없습니다.: $it") }
      .let { UserDetailsResponse.from(it) }
      .let { UsernamePasswordAuthenticationToken(it, jwtToken, it.authorities) }

  private fun getUserTokenFromJwtToken(jwtToken: String) =
    Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(jwtToken)

}