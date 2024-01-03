package me.janek.weddingmuch.domain

import me.janek.weddingmuch.infrastructure.ConsumptionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ConsumptionServiceImpl(
  private val consumptionRepository: ConsumptionRepository
): ConsumptionService {
}