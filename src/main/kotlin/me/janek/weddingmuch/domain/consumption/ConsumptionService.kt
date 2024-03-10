package me.janek.weddingmuch.domain.consumption

import me.janek.weddingmuch.api.consumption.ConsumptionCreateRequest
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.api.consumption.ConsumptionPageable

interface ConsumptionService {

  fun saveNewConsumption(createRequest: ConsumptionCreateRequest)

  fun getAllConsumption(pageCond: PageCond): ConsumptionPageable

  fun deleteConsumption(consumptionToken: String)

}