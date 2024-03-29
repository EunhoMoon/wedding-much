package me.janek.weddingmuch.api.gift

import jakarta.validation.Valid
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.domain.gift.GiftService
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
@RequestMapping("/api/gifts")
class GiftController(
  private val giftService: GiftService
) {

  @PostMapping
  fun saveGift(@RequestBody @Valid create: GiftCreateRequest): ResponseEntity<String> {
    giftService.saveNewGift(create)

    return ResponseEntity.status(HttpStatus.CREATED).body("success")
  }

  @PutMapping
  fun updateGift(@RequestBody @Valid update: GiftUpdateRequest): ResponseEntity<String> {
    giftService.updateGift(update)

    return ResponseEntity.status(HttpStatus.OK).body("success")
  }

  @GetMapping
  fun getAllGifts(pageCond: PageCond): ResponseEntity<GiftPageable> {
    val giftPageable = giftService.getAllGifts(pageCond)

    return ResponseEntity.status(HttpStatus.OK).body(giftPageable)
  }

  @DeleteMapping("/{gift-token}")
  fun deleteGift(@PathVariable("gift-token") giftToken: String): ResponseEntity<String> {
    giftService.deleteGift(giftToken)

    return ResponseEntity.status(HttpStatus.OK).body("success")
  }

}