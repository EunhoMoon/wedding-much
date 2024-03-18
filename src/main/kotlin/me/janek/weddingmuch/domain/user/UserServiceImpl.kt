package me.janek.weddingmuch.domain.user

import me.janek.weddingmuch.api.user.UserCreateRequest
import me.janek.weddingmuch.api.user.UserDetailsResponse
import me.janek.weddingmuch.infrastructure.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
  private val userRepository: UserRepository,
  private val passwordEncoder: BCryptPasswordEncoder
) : UserService {

  @Transactional
  override fun saveNewUser(createRequest: UserCreateRequest) {
    userRepository.findByUsername(createRequest.email)
      ?.let { throw IllegalArgumentException("User already exists with username: ${createRequest.email}") }

    User.of(username = createRequest.email, encryptPassword = passwordEncoder.encode(createRequest.password))
      .let { userRepository.save(it) }
  }

  override fun getUserByToken(token: String): User {
    return userRepository.findByToken(token)
      ?: throw IllegalArgumentException("User not found with token: $token")
  }

  override fun loadUserByUsername(username: String): UserDetails {
    return userRepository.findByUsername(username)
      ?.let { UserDetailsResponse.from(it) }
      ?: throw IllegalArgumentException("User not found with username: $username")
  }

}