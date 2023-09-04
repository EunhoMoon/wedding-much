package me.janek.weddingmuch.domain.gift

interface GiftService {

  fun saveNewGift()

  fun getAllGift(): List<Gift>

}