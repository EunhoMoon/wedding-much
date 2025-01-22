package me.janek.weddingmuch.domain.user

import me.janek.weddingmuch.api.user.*
import me.janek.weddingmuch.infrastructure.user.UserRepository
import me.janek.weddingmuch.domain.authority.JwtProviderImpl
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtProvider: JwtProviderImpl,
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
            ?: throw IllegalArgumentException("User not found with username: $token")
    }

    override fun login(loginRequest: UserLoginRequest): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtProvider.generateToken(authentication.principal as UserDetailsResponse)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return userRepository.findByUsername(username)
            ?.let { UserDetailsResponse.from(it) }
            ?: throw IllegalArgumentException("User not found with username: $username")
    }

    override fun refreshAccessToken(tokenRefreshRequest: TokenRequest): TokenInfo {
        val regenerateToken = jwtProvider.regenerateAccessToken(tokenRefreshRequest.refreshToken)
        return TokenInfo(accessToken = regenerateToken, refreshToken = tokenRefreshRequest.refreshToken)
    }

}