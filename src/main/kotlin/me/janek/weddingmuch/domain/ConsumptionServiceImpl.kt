package me.janek.weddingmuch.domain

import me.janek.weddingmuch.api.ConsumptionCreateRequest
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.infrastructure.ConsumptionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ConsumptionServiceImpl(
  private val consumptionRepository: ConsumptionRepository
) : ConsumptionService {

  @Transactional
  override fun saveNewConsumption(createRequest: ConsumptionCreateRequest) {
    val newConsumption = Consumption.of(createRequest)
    consumptionRepository.save(newConsumption)
  }

  override fun getConsumptionList(pageCond: PageCond): List<Consumption> = consumptionRepository.findAllConsumptions(pageCond)

  override fun getTotalConsumption(): Long = consumptionRepository.count()

  override fun deleteConsumption(consumptionToken: String) = consumptionRepository.deleteByToken(consumptionToken)

}