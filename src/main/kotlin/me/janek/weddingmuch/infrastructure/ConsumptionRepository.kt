package me.janek.weddingmuch.infrastructure

import me.janek.weddingmuch.domain.Consumption
import org.springframework.data.jpa.repository.JpaRepository

interface ConsumptionRepository : JpaRepository<Consumption, Long>, ConsumptionRepositoryCustom {

  fun deleteByToken(consumptionToken: String)

}