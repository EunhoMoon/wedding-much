package me.janek.weddingmuch.infrastructure

import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.domain.Gift

interface GiftRepositoryCustom {

  fun findAllGifts(pageCond: PageCond): List<Gift>

}
