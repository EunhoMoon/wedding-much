package me.janek.weddingmuch.domain

import me.janek.weddingmuch.api.CreateRequest
import me.janek.weddingmuch.api.PageCond

interface GiftService {

  fun saveNewGift(createRequest: CreateRequest)

  fun getGiftList(pageCond: PageCond): List<Gift>

  fun getTotalGift(): Long

}