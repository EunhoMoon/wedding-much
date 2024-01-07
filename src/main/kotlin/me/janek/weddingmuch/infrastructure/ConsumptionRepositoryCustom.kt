package me.janek.weddingmuch.infrastructure

import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.domain.Consumption

interface ConsumptionRepositoryCustom {

  fun findAllConsumptions(pageCond: PageCond): List<Consumption>

}