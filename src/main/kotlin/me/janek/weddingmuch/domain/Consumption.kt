package me.janek.weddingmuch.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "consumptions")
class Consumption(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = 0,

  @Column(nullable = false)
  val token: String,

  @Column(nullable = false)
  val place: String,

  @Column(nullable = false)
  val price: Int,

  @Column(nullable = true)
  val memo: String?,

) {
}