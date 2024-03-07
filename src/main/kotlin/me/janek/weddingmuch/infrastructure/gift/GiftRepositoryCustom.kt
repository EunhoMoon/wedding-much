package me.janek.weddingmuch.infrastructure.gift

import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.domain.gift.Gift

interface GiftRepositoryCustom {

  fun findAllGifts(pageCond: PageCond): List<Gift>

}
