package me.janek.weddingmuch.infrastructure

import me.janek.weddingmuch.domain.Gift
import org.springframework.data.jpa.repository.JpaRepository

interface GiftRepository : JpaRepository<Gift, Long>, GiftRepositoryCustom {

  fun deleteByToken(giftToken: String)

}