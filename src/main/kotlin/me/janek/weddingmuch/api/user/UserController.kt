package me.janek.weddingmuch.api.user

import me.janek.weddingmuch.domain.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
  private val userService: UserService
) {

  @PostMapping("/signup")
  fun saveUser(@RequestBody create: UserCreateRequest): ResponseEntity<String> {
    userService.saveNewUser(create)
    return ResponseEntity.ok("success")
  }

  @PostMapping("/login")
  fun login(@RequestBody login: UserLoginRequest): ResponseEntity<TokenInfo> {
    val token = userService.login(login)
    return ResponseEntity.ok(token)
  }

  @PostMapping("/refresh")
  fun refreshAccessToken(@RequestBody tokenRefreshRequest: TokenRequest): ResponseEntity<TokenInfo> {
    val token = userService.refreshAccessToken(tokenRefreshRequest)
    return ResponseEntity.ok(token)
  }

}