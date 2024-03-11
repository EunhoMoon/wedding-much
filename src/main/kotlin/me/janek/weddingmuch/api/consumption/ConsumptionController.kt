package me.janek.weddingmuch.api.consumption

import jakarta.validation.Valid
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.domain.consumption.ConsumptionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/consumptions")
class ConsumptionController(
  private val consumptionService: ConsumptionService
) {

  @PostMapping
  fun saveConsumption(@RequestBody @Valid create: ConsumptionCreateRequest): ResponseEntity<String> {
    consumptionService.saveNewConsumption(create)

    return ResponseEntity.status(HttpStatus.CREATED).body("success")
  }

  @PutMapping
  fun updateConsumption(@RequestBody @Valid update: ConsumptionUpdateRequest): ResponseEntity<String> {
    consumptionService.updateConsumption(update)

    return ResponseEntity.status(HttpStatus.OK).body("success")
  }

  @GetMapping
  fun getAllConsumptions(pageCond: PageCond): ResponseEntity<ConsumptionPageable> {
    val consumptionPageable = consumptionService.getAllConsumption(pageCond)

    return ResponseEntity.status(HttpStatus.OK).body(consumptionPageable)
  }

  @DeleteMapping("/{consumption-token}")
  fun deleteConsumption(@PathVariable("consumption-token") consumptionToken: String): ResponseEntity<String> {
    consumptionService.deleteConsumption(consumptionToken)

    return ResponseEntity.status(HttpStatus.OK).body("success")
  }

}