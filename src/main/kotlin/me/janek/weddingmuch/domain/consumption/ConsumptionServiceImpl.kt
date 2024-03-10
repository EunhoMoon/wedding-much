package me.janek.weddingmuch.domain.consumption

import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.api.consumption.ConsumptionCreateRequest
import me.janek.weddingmuch.api.consumption.ConsumptionInfoResponse
import me.janek.weddingmuch.api.consumption.ConsumptionPageable
import me.janek.weddingmuch.api.consumption.ConsumptionUpdateRequest
import me.janek.weddingmuch.infrastructure.consumption.ConsumptionRepository
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

  @Transactional
  override fun updateConsumption(updateRequest: ConsumptionUpdateRequest) {
    val consumption = consumptionRepository.findByToken(updateRequest.token!!)
      ?: throw IllegalArgumentException("Consumption not found with token: ${updateRequest.token}")
    consumption.update(updateRequest)
  }

  override fun getAllConsumption(pageCond: PageCond): ConsumptionPageable {
    val allConsumptions = consumptionRepository.findAllConsumptions(pageCond).map(ConsumptionInfoResponse.Companion::of)
    val total = consumptionRepository.count()

    return ConsumptionPageable(total = total, pageCond = pageCond, list = allConsumptions)
  }

  @Transactional
  override fun deleteConsumption(consumptionToken: String) = consumptionRepository.deleteByToken(consumptionToken)

}