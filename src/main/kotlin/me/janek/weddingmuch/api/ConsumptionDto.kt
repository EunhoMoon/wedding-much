package me.janek.weddingmuch.api

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import me.janek.weddingmuch.domain.Consumption

data class ConsumptionCreateRequest(
  @field:NotEmpty(message = "소비내역은 필수입니다.")
  val place: String?,
  @field:NotNull(message = "소비금액은 필수입니다.")
  val price: Int?,
  val memo: String?
)

data class ConsumptionUpdateRequest(
  @field:NotNull(message = "토큰은 필수입니다.")
  val token: String?,
  @field:NotEmpty(message = "소비내역은 필수입니다.")
  val place: String?,
  @field:NotNull(message = "소비금액은 필수입니다.")
  val price: Int?,
  val memo: String?
)

data class ConsumptionInfoResponse(
  val place: String,
  val price: Int,
  val memo: String?,
  val token: String
) {
  companion object {
    fun of(consumption: Consumption): ConsumptionInfoResponse = ConsumptionInfoResponse(
      place = consumption.place,
      price = consumption.price,
      memo = consumption.memo,
      token = consumption.token
    )
  }
}

class ConsumptionPageable(
  total: Long,
  pageCond: PageCond,
  list: List<ConsumptionInfoResponse>
) : Pageable<ConsumptionInfoResponse>(total, pageCond, list) {
  val totalPrice: Int = list.map(ConsumptionInfoResponse::price).sum()
}