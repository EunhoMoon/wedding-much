package me.janek.weddingmuch.utils

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import me.janek.weddingmuch.api.user.UserDetailsResponse
import me.janek.weddingmuch.domain.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.time.Duration
import kotlin.time.toJavaDuration

@Component
class JwtProvider(
  private val jwtProperties: JwtProperties,
  private val userService: UserService
) {

  private val SECRET_KEY = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8))

  private val log = LoggerFactory.getLogger(this::class.java)

  fun generateToken(user: UserDetailsResponse, expiredAt: Duration): String {
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
      .signWith(SECRET_KEY, Jwts.SIG.HS512)
      .compact()

  fun verifyToken(jwtToken: String): Boolean {
    try {
      getUserTokenFromJwtToken(jwtToken)

      return true
    } catch (e: SecurityException) {
      log.info("잘못된 서명입니다.")
    } catch (e: MalformedJwtException) {
      log.info("잘못된 서명입니다.")
    } catch (e: UnsupportedJwtException) {
      log.info("지원되지 않는 토큰입니다.")
    } catch (e: IllegalArgumentException) {
      log.info("잘못된 토큰입니다.")
    }

    return false
  }

  fun getAuthentication(jwtToken: String): Authentication =
    getUserTokenFromJwtToken(jwtToken).toString()
      .let { userService.getUserByToken(it) }
      .let { UserDetailsResponse.from(it) }
      .let { UsernamePasswordAuthenticationToken(it, jwtToken, it.authorities) }

  private fun getUserTokenFromJwtToken(jwtToken: String) =
    Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(jwtToken)

}