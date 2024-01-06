package me.janek.weddingmuch.api

import jakarta.validation.constraints.NotEmpty

data class ConsumptionCreateRequest(
  @field:NotEmpty(message = "소비내역은 필수입니다.")
  val place: String?,
  @field:NotEmpty(message = "소비금액은 필수입니다.")
  val price: Int?,
  val memo: String?
) {
}