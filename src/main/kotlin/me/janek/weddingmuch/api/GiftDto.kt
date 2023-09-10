package me.janek.weddingmuch.api

import me.janek.weddingmuch.domain.Gift

data class CreateRequest(
  val name: String,
  val price: Int,
  val memo: String?
)

data class UpdateRequest(
  val name: String,
  val price: Int,
  val memo: String?,
  val token: String
)

data class InfoResponse(
  val name: String,
  val price: Int,
  val memo: String?,
  val token: String
) {
  companion object {
    fun of(gift: Gift): InfoResponse = InfoResponse(
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
  list: List<InfoResponse>
) : Pageable<InfoResponse>(total, pageCond, list) {
  val totalPrice: Int = list.map(InfoResponse::price).sum()
}