package me.janek.weddingmuch.api

import me.janek.weddingmuch.domain.GiftService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/gifts")
class GiftController(
  private val giftService: GiftService
) {

  @PostMapping
  fun saveGift(@RequestBody create: CreateRequest): ResponseEntity<String> {
    giftService.saveNewGift(create)

    return ResponseEntity.status(HttpStatus.CREATED).body("success")
  }

  @GetMapping
  fun getAllGifts(pageCond: PageCond): ResponseEntity<GiftPageable> {
    val list = giftService.getGiftList(pageCond).map(InfoResponse.Companion::of)
    val total = giftService.getTotalGift()
    val giftPageable = GiftPageable(total, pageCond, list)

    return ResponseEntity.status(HttpStatus.OK).body(giftPageable)
  }

  @DeleteMapping("/{gift-token}")
  fun deleteGift(@PathVariable("gift-token") giftToken: String): ResponseEntity<String> {
    giftService.deleteGift(giftToken)

    return ResponseEntity.status(HttpStatus.OK).body("success")
  }

}