package me.janek.weddingmuch.api.user

import me.janek.weddingmuch.domain.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
  private val userService: UserService,
  private val authenticationManagerBuilder: AuthenticationManagerBuilder
) {

  @PostMapping("/signup")
  fun saveUser(@RequestBody create: UserCreateRequest): ResponseEntity<String> {
    userService.saveNewUser(create)
    return ResponseEntity.ok("success")
  }

  @PostMapping("/login")
  fun login(@RequestBody login: UserLoginRequest): ResponseEntity<String> {
    return ResponseEntity.ok("success")
  }

}