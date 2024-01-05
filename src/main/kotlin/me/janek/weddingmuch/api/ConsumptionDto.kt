package me.janek.weddingmuch.api

data class ConsumptionCreateRequest(
  val place: String,
  val price: Int
) {
}