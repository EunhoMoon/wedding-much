package me.janek.weddingmuch.domain.user

import me.janek.weddingmuch.api.user.UserCreateRequest
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

  fun saveNewUser(createRequest: UserCreateRequest)

  fun getUserByToken(token: String): User

}