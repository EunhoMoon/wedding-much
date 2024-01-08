package me.janek.weddingmuch.domain

import me.janek.weddingmuch.api.GiftCreateRequest
import me.janek.weddingmuch.api.GiftUpdateRequest
import me.janek.weddingmuch.api.PageCond

interface GiftService {

  fun saveNewGift(createRequest: GiftCreateRequest)

  fun updateGift(updateRequest: GiftUpdateRequest)

  fun getGiftList(pageCond: PageCond): List<Gift>

  fun getTotalGift(): Long

  fun deleteGift(giftToken: String)

}