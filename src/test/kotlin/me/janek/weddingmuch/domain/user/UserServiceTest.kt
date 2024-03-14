package me.janek.weddingmuch.domain.user

import me.janek.weddingmuch.IntegrationTestSupport
import me.janek.weddingmuch.api.user.UserCreateRequest
import me.janek.weddingmuch.infrastructure.user.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class UserServiceTest @Autowired constructor(
  private val userService: UserService,
  private val userRepository: UserRepository
) : IntegrationTestSupport() {

  @Test
  @DisplayName("신규 회원이 정상 저장된다. 비밀번호는 암호화되어 저장된다.")
  fun saveNewUser() {
    // given
    val request = UserCreateRequest(email = "test@co.kr", password = "1234")

    // when
    userService.saveNewUser(request)

    // then
    val findUser = userRepository.findByUsername("test@co.kr")
    assertThat(findUser).isNotNull
    assertThat(findUser!!.username).isEqualTo("test@co.kr")
    assertThat(findUser.password).isNotEqualTo("1234")
  }

  @Test
  @DisplayName("신규 회원 저장시 이미 존재하는 회원이름일 경우 예외가 발생한다.")
  fun saveNewUserWhenUsernameAlreadyExists() {
    // given
    User(token = "token", username = "test@co.kr", password = "1234").let { userRepository.save(it) }
    val request = UserCreateRequest(email = "test@co.kr", password = "1234")

    // expect
    assertThatThrownBy { userService.saveNewUser(request) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("User already exists with username: ${request.email}")
  }

  @Test
  @DisplayName("회원 이메일로 조회가 정상 작동한다.")
  fun loadUserByUsername() {
    // given
    User(token = "token", username = "test@co.kr", password = "1234").let { userRepository.save(it) }

    // when
    val userDetails = userService.loadUserByUsername("test@co.kr")

    // then
    assertThat(userDetails).isNotNull
      .extracting("token", "username")
      .containsExactly("token", "test@co.kr")
  }


  @Test
  @DisplayName("회원 이메일로 조회시 해당하는 회원이 없을 경우 예외가 발생한다.")
  fun loadUserByUsernameWhenUserIsNotExists() {
    // given
    val username = "test@co.kr"

    // expect
    assertThatThrownBy { userService.loadUserByUsername(username) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("User not found with username: $username")
  }

}