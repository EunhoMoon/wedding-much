package me.janek.weddingmuch.domain.gift

import me.janek.weddingmuch.api.gift.GiftCreateRequest
import me.janek.weddingmuch.api.gift.GiftPageable
import me.janek.weddingmuch.api.gift.GiftUpdateRequest
import me.janek.weddingmuch.api.PageCond

interface GiftService {

  fun saveNewGift(createRequest: GiftCreateRequest)

  fun updateGift(updateRequest: GiftUpdateRequest)

  fun getAllGifts(pageCond: PageCond): GiftPageable

  fun deleteGift(giftToken: String)

}