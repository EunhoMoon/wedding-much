package me.janek.weddingmuch.domain.consumption

import jakarta.persistence.*
import me.janek.weddingmuch.api.consumption.ConsumptionCreateRequest
import me.janek.weddingmuch.api.consumption.ConsumptionUpdateRequest
import me.janek.weddingmuch.domain.BaseEntity
import java.util.*


@Entity
@Table(name = "consumptions")
class Consumption(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = 0,

  @Column(nullable = false, unique = true)
  val token: String,

  @Column(nullable = false)
  var place: String,

  @Column(nullable = false)
  var price: Int,

  @Column(nullable = true)
  var memo: String?,

  ): BaseEntity() {

  companion object {
    fun of(request: ConsumptionCreateRequest): Consumption = Consumption(
      place = request.place!!,
      price = request.price!!,
      memo = request.memo,
      token = UUID.randomUUID().toString()
    )
  }

  fun update(request: ConsumptionUpdateRequest) {
    if (request.place.isNullOrBlank()) throw IllegalArgumentException("소비내역을 입력해주세요.")
    request.price ?: throw IllegalArgumentException("금액을 입력해주세요.")
    this.place = request.place
    this.price = request.price
    this.memo = request.memo
  }
}