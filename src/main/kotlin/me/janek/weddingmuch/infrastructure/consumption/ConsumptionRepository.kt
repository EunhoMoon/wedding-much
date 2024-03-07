package me.janek.weddingmuch.infrastructure.consumption

import me.janek.weddingmuch.domain.consumption.Consumption
import org.springframework.data.jpa.repository.JpaRepository

interface ConsumptionRepository : JpaRepository<Consumption, Long>, ConsumptionRepositoryCustom {

  fun deleteByToken(consumptionToken: String)

}