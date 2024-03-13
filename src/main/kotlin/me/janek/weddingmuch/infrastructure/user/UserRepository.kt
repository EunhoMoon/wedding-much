package me.janek.weddingmuch.infrastructure.user

import me.janek.weddingmuch.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

  fun findByToken(userToken: String): User?

  fun findByUsername(username: String): User?

}