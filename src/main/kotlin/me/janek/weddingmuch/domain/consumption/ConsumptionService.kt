package me.janek.weddingmuch.domain.consumption

import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.api.consumption.ConsumptionCreateRequest
import me.janek.weddingmuch.api.consumption.ConsumptionPageable
import me.janek.weddingmuch.api.consumption.ConsumptionUpdateRequest

interface ConsumptionService {

  fun saveNewConsumption(createRequest: ConsumptionCreateRequest)

  fun updateConsumption(updateRequest: ConsumptionUpdateRequest)

  fun getAllConsumption(pageCond: PageCond): ConsumptionPageable

  fun deleteConsumption(consumptionToken: String)

}