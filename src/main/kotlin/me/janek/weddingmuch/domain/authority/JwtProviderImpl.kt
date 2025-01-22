package me.janek.weddingmuch.domain.authority

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import me.janek.weddingmuch.api.user.TokenInfo
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
class JwtProviderImpl(
    private val jwtProperties: JwtProperties,
    private val userRepository: UserRepository
) : JwtProvider {

    private val SECRET_KEY = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8))

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun generateToken(
        user: UserDetailsResponse,
        accessTokenExpiredAt: Duration,
        refreshTokenExpiredAt: Duration
    ): TokenInfo {
        val now = Date()
        val accessToken = createToken(Date(now.time + accessTokenExpiredAt.toJavaDuration().toMillis()), user)
        val refreshToken = createToken(Date(now.time + refreshTokenExpiredAt.toJavaDuration().toMillis()), user)

        return TokenInfo(accessToken = accessToken, refreshToken = refreshToken)
    }

    override fun verifyToken(jwtToken: String): Boolean {
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

    override fun getAuthentication(jwtToken: String): Authentication =
        getUserTokenFromJwtToken(jwtToken)
            .let { userRepository.findByToken(it) ?: throw IllegalArgumentException("해당하는 사용자를 찾을 수 없습니다.: $it") }
            .let { UserDetailsResponse.from(it) }
            .let { UsernamePasswordAuthenticationToken(it, jwtToken, it.authorities) }

    override fun regenerateAccessToken(refreshToken: String): String {
        if (!verifyToken(refreshToken)) {
            throw IllegalArgumentException("유효하지 않은 Refresh Token입니다.")
        }

        val user = getUserTokenFromJwtToken(refreshToken)
            .let { userRepository.findByToken(it) ?: throw IllegalArgumentException("해당하는 사용자를 찾을 수 없습니다: $it") }

        return createToken(
            Date(Date().time + 10.toDuration(DurationUnit.MINUTES).toJavaDuration().toMillis()),
            UserDetailsResponse.from(user)
        )
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

    private fun getUserTokenFromJwtToken(jwtToken: String): String {
        val claims = getClaims(jwtToken)
        val userToken = claims["userToken"] ?: throw IllegalArgumentException("User Token이 없습니다.")
        return userToken.toString()
    }

    private fun getClaims(jwtToken: String) =
        Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(jwtToken).payload

}