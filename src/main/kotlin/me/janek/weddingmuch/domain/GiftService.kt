package me.janek.weddingmuch.domain

import me.janek.weddingmuch.api.GiftCreateRequest
import me.janek.weddingmuch.api.PageCond

interface GiftService {

  fun saveNewGift(createRequest: GiftCreateRequest)

  fun getGiftList(pageCond: PageCond): List<Gift>

  fun getTotalGift(): Long

  fun deleteGift(giftToken: String)

}