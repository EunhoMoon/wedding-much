package me.janek.weddingmuch.domain.user

import me.janek.weddingmuch.infrastructure.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
  private val userRepository: UserRepository
) : UserService {

  override fun loadUserByUsername(username: String?): UserDetails {
    TODO("Not yet implemented")
  }

}