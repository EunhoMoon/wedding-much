package me.janek.weddingmuch.infrastructure.gift

import me.janek.weddingmuch.domain.gift.Gift
import org.springframework.data.jpa.repository.JpaRepository

interface GiftRepository : JpaRepository<Gift, Long>, GiftRepositoryCustom {

  fun findByToken(giftToken: String): Gift?

  fun deleteByToken(giftToken: String)

}