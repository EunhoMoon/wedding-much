package me.janek.weddingmuch.domain

import me.janek.weddingmuch.api.ConsumptionCreateRequest
import me.janek.weddingmuch.api.PageCond

interface ConsumptionService {

  fun saveNewConsumption(createRequest: ConsumptionCreateRequest)

  fun getConsumptionList(pageCond: PageCond): List<Consumption>

  fun getTotalConsumption(): Long

  fun deleteConsumption(consumptionToken: String)

}