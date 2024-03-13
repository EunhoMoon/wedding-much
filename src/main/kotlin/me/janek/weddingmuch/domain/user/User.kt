package me.janek.weddingmuch.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.janek.weddingmuch.domain.BaseEntity

@Entity
@Table(name = "users")
class User(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = 0,

  @Column(nullable = false, unique = true)
  val token: String,

  @Column(nullable = false, unique = true)
  val username: String,

  @Column(nullable = false)
  val password: String,

) : BaseEntity() {
}