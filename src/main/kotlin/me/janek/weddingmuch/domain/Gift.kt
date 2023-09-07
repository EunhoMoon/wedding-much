package me.janek.weddingmuch.domain

import jakarta.persistence.*
import me.janek.weddingmuch.api.CreateRequest
import java.util.UUID

@Entity
@Table(name = "gifts")
class Gift(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = 0,

  @Column(nullable = false)
  val token: String,

  @Column(nullable = false)
  val name: String,

  @Column(nullable = true)
  val price: Int,

  val memo: String?,

  ) {

  init {
    if (name.isBlank()) throw IllegalArgumentException("이름을 입력해주세요.")
  }

  companion object {
    fun of(request: CreateRequest): Gift = Gift(
      name = request.name,
      memo = request.memo,
      price = request.price,
      token = UUID.randomUUID().toString()
    )
  }
}