package me.janek.weddingmuch.domain.user

import me.janek.weddingmuch.api.user.UserCreateRequest
import me.janek.weddingmuch.api.user.UserLoginRequest
import me.janek.weddingmuch.api.user.TokenInfo
import me.janek.weddingmuch.api.user.TokenRequest
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService {

  fun saveNewUser(createRequest: UserCreateRequest)

  fun getUserByToken(token: String): User

  fun login(loginRequest: UserLoginRequest): TokenInfo

  fun refreshAccessToken(tokenRefreshRequest: TokenRequest): TokenInfo

}