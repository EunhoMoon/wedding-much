package me.janek.weddingmuch.api.gift

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.api.Pageable
import me.janek.weddingmuch.domain.gift.Gift

data class GiftCreateRequest(
  @field:NotEmpty(message = "이름은 필수입니다.")
  val name: String?,
  @field:NotNull(message = "가격은 필수입니다.")
  val price: Int?,
  val memo: String?
)

data class GiftUpdateRequest(
  @field:NotEmpty(message = "토큰은 필수입니다.")
  val token: String?,
  @field:NotEmpty(message = "이름은 필수입니다.")
  val name: String?,
  @field:NotNull(message = "가격은 필수입니다.")
  val price: Int?,
  val memo: String?
)

data class GiftInfoResponse(
  val name: String,
  val price: Int,
  val memo: String?,
  val token: String
) {
  companion object {
    fun of(gift: Gift): GiftInfoResponse = GiftInfoResponse(
      name = gift.name,
      price = gift.price,
      memo = gift.memo,
      token = gift.token
    )
  }
}

class GiftPageable(
  total: Long,
  pageCond: PageCond,
  list: List<GiftInfoResponse>
) : Pageable<GiftInfoResponse>(total, pageCond, list) {
  val totalPrice: Int = list.map(GiftInfoResponse::price).sum()
}