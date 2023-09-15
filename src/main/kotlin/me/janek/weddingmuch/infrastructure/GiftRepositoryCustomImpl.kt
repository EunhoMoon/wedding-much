package me.janek.weddingmuch.infrastructure

import com.querydsl.jpa.impl.JPAQueryFactory
import me.janek.weddingmuch.api.Direction
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.api.Sort
import me.janek.weddingmuch.domain.Gift
import me.janek.weddingmuch.domain.QGift.gift

class GiftRepositoryCustomImpl(
  private val queryFactory: JPAQueryFactory
) : GiftRepositoryCustom {

  override fun findAllGifts(pageCond: PageCond): List<Gift> {
    return queryFactory.selectFrom(gift)
      .orderBy(getSort(pageCond))
      .limit(pageCond.size)
      .offset(pageCond.offset)
      .fetch();
  }

  private fun getSort(pageCond: PageCond) = when (pageCond.sort) {
    Sort.ID -> gift.id
    Sort.NAME -> gift.name
    Sort.PRICE -> gift.price
  }.run {
    if (pageCond.direction == Direction.ASC) this.asc() else this.desc()
  }

}