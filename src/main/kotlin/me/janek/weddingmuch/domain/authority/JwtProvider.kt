package me.janek.weddingmuch.domain.authority

import me.janek.weddingmuch.api.user.TokenInfo
import me.janek.weddingmuch.api.user.UserDetailsResponse
import org.springframework.security.core.Authentication
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

interface JwtProvider {

    fun generateToken(
        user: UserDetailsResponse,
        accessTokenExpiredAt: Duration = 10.toDuration(DurationUnit.MINUTES),
        refreshTokenExpiredAt: Duration = 2.toDuration(DurationUnit.HOURS)
    ): TokenInfo

    fun verifyToken(jwtToken: String): Boolean

    fun getAuthentication(jwtToken: String): Authentication

    fun regenerateAccessToken(refreshToken: String): String

}