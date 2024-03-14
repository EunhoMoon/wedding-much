package me.janek.weddingmuch.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.janek.weddingmuch.domain.BaseEntity
import java.util.UUID

@Entity
@Table(name = "users")
class User(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = 0,

  @Column(nullable = false, unique = true)
  var token: String,

  @Column(nullable = false, unique = true)
  var username: String,

  @Column(nullable = false)
  var password: String,

) : BaseEntity() {

  init {
    if (username.isBlank()) throw IllegalArgumentException("Username을 입력해주세요.")
    if (password.isBlank()) throw IllegalArgumentException("Password를 입력해주세요.")
  }

  companion object {
    fun of(username: String, encryptPassword: String): User = User(
      username = username,
      password = encryptPassword,
      token = UUID.randomUUID().toString()
    )
  }

  fun update(newEncryptPassword: String) {
    this.password = newEncryptPassword
  }
}