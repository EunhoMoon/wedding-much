package me.janek.weddingmuch.api

import me.janek.weddingmuch.domain.Gift

class CreateRequest(
  val name: String,
  val price: Int,
  val memo: String?
)

class UpdateRequest(
  val name: String,
  val price: Int,
  val memo: String?,
  val token: String
)

class InfoResponse(
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