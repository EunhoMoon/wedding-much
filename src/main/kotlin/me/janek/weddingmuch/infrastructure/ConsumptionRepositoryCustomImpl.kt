package me.janek.weddingmuch.infrastructure

import com.querydsl.jpa.impl.JPAQueryFactory
import me.janek.weddingmuch.api.Direction
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.api.Sort
import me.janek.weddingmuch.domain.Consumption
import me.janek.weddingmuch.domain.QConsumption.consumption

class ConsumptionRepositoryCustomImpl(
  private val queryFactory: JPAQueryFactory
) : ConsumptionRepositoryCustom {

  override fun findAllConsumptions(pageCond: PageCond): List<Consumption> {
    return queryFactory.selectFrom(consumption)
      .orderBy(getSort(pageCond))
      .limit(pageCond.size)
      .offset(pageCond.offset)
      .fetch();
  }

  private fun getSort(pageCond: PageCond) = when (pageCond.sort) {
    Sort.ID -> consumption.id
    Sort.NAME -> consumption.place
    Sort.PRICE -> consumption.price
  }.run {
    if (pageCond.direction == Direction.ASC) this.asc() else this.desc()
  }

}