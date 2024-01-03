package me.janek.weddingmuch.api

import me.janek.weddingmuch.domain.ConsumptionService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/consumptions")
class ConsumptionController(
  private val consumptionService: ConsumptionService
) {
}