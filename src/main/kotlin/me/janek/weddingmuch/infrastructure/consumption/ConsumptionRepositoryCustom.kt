package me.janek.weddingmuch.infrastructure.consumption

import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.domain.consumption.Consumption

interface ConsumptionRepositoryCustom {

  fun findAllConsumptions(pageCond: PageCond): List<Consumption>

}