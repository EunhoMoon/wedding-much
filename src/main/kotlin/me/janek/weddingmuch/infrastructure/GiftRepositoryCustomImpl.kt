package me.janek.weddingmuch.infrastructure

import com.querydsl.jpa.impl.JPAQueryFactory
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.domain.Gift
import me.janek.weddingmuch.domain.QGift.gift

class GiftRepositoryCustomImpl(
  private val queryFactory: JPAQueryFactory
) : GiftRepositoryCustom {

  override fun findAllGifts(pageCond: PageCond): List<Gift> {
    return queryFactory.selectFrom(gift)
      .orderBy(gift.id.desc())
      .limit(pageCond.size)
      .offset(pageCond.offset)
      .fetch();
  }

}