package me.janek.weddingmuch.utils

import me.janek.weddingmuch.IntegrationTestSupport
import me.janek.weddingmuch.api.user.UserDetailsResponse
import me.janek.weddingmuch.domain.user.User
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class JwtProviderTest @Autowired constructor(
  private val jwtProvider: JwtProvider
) : IntegrationTestSupport() {

  @Test
  @DisplayName("jwt 토큰 생성이 정상 작동한다.")
  fun generateToken() {
    // given
    val userDetails = UserDetailsResponse.from(User(id = 1, username = "test", password = "test", token = "test"))

    // when
    val generateToken = jwtProvider.generateToken(user = userDetails, expiredAt = 10.toDuration(DurationUnit.MINUTES))

    // then
    generateToken
      .let { jwtProvider.verifyToken(it) }
      .let {
        Assertions.assertThat(it).isEqualTo("test")
      }
  }

}