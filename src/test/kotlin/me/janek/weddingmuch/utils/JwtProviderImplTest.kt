package me.janek.weddingmuch.utils

import me.janek.weddingmuch.IntegrationTestSupport
import me.janek.weddingmuch.api.user.UserDetailsResponse
import me.janek.weddingmuch.domain.authority.JwtProviderImpl
import me.janek.weddingmuch.domain.user.User
import me.janek.weddingmuch.infrastructure.user.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class JwtProviderImplTest @Autowired constructor(
    private val jwtProvider: JwtProviderImpl,
    private val userRepository: UserRepository
) : IntegrationTestSupport() {

    @Test
    @DisplayName("jwt 토큰 생성이 정상 작동한다.")
    fun generateToken() {
        // given
        val userDetails = UserDetailsResponse.from(User(id = 1, username = "test", password = "test", token = "test"))

        // when
        val generateToken =
            jwtProvider.generateToken(user = userDetails, accessTokenExpiredAt = 10.toDuration(DurationUnit.MINUTES))

        // then
        assertThat(generateToken.accessToken).isNotNull
        assertThat(jwtProvider.verifyToken(generateToken.accessToken)).isTrue
        assertThat(generateToken.refreshToken).isNotNull
        assertThat(jwtProvider.verifyToken(generateToken.refreshToken)).isTrue
    }

    @Test
    @DisplayName("jwt 토큰 검증이 정상 작동한다.")
    fun verifyToken() {
        // given
        val userDetails = UserDetailsResponse.from(User(id = 1, username = "test", password = "test", token = "test"))
        val generateToken =
            jwtProvider.generateToken(user = userDetails, accessTokenExpiredAt = 10.toDuration(DurationUnit.MINUTES))

        // when
        val verifyAccessToken = jwtProvider.verifyToken(generateToken.accessToken)
        val verifyRefreshToken = jwtProvider.verifyToken(generateToken.refreshToken)

        // then
        assertThat(verifyAccessToken).isTrue
        assertThat(verifyRefreshToken).isTrue
    }

    @Test
    @DisplayName("jwt 토큰 검증이 실패한다.")
    fun verifyTokenFail() {
        // given
        val invalidToken = "invalid token"

        // when
        val verifyToken = jwtProvider.verifyToken(invalidToken)

        // then
        assertThat(verifyToken).isFalse
    }

    @Test
    @DisplayName("Refresh 토큰을 통한 AccessToken 재발급이 정상 작동한다.")
    fun refreshToken() {
        // given
        userRepository.save(User(id = 1, username = "test", password = "test", token = "test"))
        val userDetails = UserDetailsResponse.from(User(id = 1, username = "test", password = "test", token = "test"))
        val refreshToken = jwtProvider.generateToken(
            user = userDetails,
            accessTokenExpiredAt = 10.toDuration(DurationUnit.MINUTES)
        ).refreshToken

        // when
        val regenerateAccessToken = jwtProvider.regenerateAccessToken(refreshToken)

        // then
        assertThat(jwtProvider.verifyToken(regenerateAccessToken)).isTrue
    }

}