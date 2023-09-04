package me.janek.weddingmuch.domain.gift

import jakarta.persistence.*

@Entity
@Table(name = "gifts")
class Gift(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = 0,

  val name: String,

  val price: Int,

  val memo: String,

) {
  init {
    if (name.isBlank()) throw IllegalArgumentException("이름을 입력해주세요.");
  }
}