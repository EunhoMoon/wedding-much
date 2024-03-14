package me.janek.weddingmuch.api.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import me.janek.weddingmuch.domain.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserCreateRequest(
  @field:NotEmpty(message = "Email은 필수입니다.")
  @field:Email(message = "Email 형식이 아닙니다.")
  val email: String,
  @field:NotEmpty(message = "비밀번호는 필수입니다.")
  val password: String,
)

class UserDetailsResponse private constructor(
  var token: String,
  @JvmField
  var username: String,
  @JvmField
  var password: String,
  @JvmField
  var authorities: Collection<GrantedAuthority>
) : UserDetails {

  companion object {
    fun from(user: User): UserDetails {
      return UserDetailsResponse(
        token = user.token,
        username = user.username,
        password = user.password,
        authorities = mutableSetOf(GrantedAuthority { "USER" })
      )
    }
  }

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    return authorities.toMutableList()
  }

  override fun getPassword(): String {
    return password
  }

  override fun getUsername(): String {
    return username
  }

  // TODO: development these methods
  override fun isAccountNonExpired(): Boolean {
    return true
  }

  override fun isAccountNonLocked(): Boolean {
    return true
  }

  override fun isCredentialsNonExpired(): Boolean {
    return true
  }

  override fun isEnabled(): Boolean {
    return true
  }

}