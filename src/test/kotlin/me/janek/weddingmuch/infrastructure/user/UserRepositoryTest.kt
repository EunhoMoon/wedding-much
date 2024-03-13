package me.janek.weddingmuch.infrastructure.user

import me.janek.weddingmuch.IntegrationTestSupport
import me.janek.weddingmuch.domain.user.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class UserRepositoryTest @Autowired constructor(
  private val userRepository: UserRepository
) : IntegrationTestSupport() {

  @Test
  @DisplayName("토큰을 통해 회원을 조회한다.")
  fun findByToken() {
    // given
    User(token = "token1", username = "username1", password = "password1")
      .let { userRepository.save(it) }

    // when
    val user = userRepository.findByToken("token1")

    // then
    assertThat(user).isNotNull
      .extracting("token", "username", "password")
      .containsExactly("token1", "username1", "password1")
  }

  @Test
  @DisplayName("토큰을 통해 회원을 조회한다. 토큰이 없는 경우 null을 반환한다.")
  fun findByTokenWhenTokenIsNotExists() {
    // given
    val user = userRepository.findByToken("not_exists_token")

    // expect
    assertThat(user).isNull()
  }

  @Test
  @DisplayName("username을 통해 회원을 조회한다.")
  fun findByUsername() {
    // given
    User(token = "token1", username = "username1", password = "password1")
      .let { userRepository.save(it) }

    // when
    val user = userRepository.findByUsername("username1")

    // expect
    assertThat(user).isNotNull
      .extracting("token", "username", "password")
      .containsExactly("token1", "username1", "password1")
  }

  @Test
  @DisplayName("username을 통해 회원을 조회한다. username이 없는 경우 null을 반환한다.")
  fun findByUsernameWhenUsernameIsNotExists() {
    // given
    val user = userRepository.findByUsername("not_exists_username")

    // expect
    assertThat(user).isNull()
  }

}