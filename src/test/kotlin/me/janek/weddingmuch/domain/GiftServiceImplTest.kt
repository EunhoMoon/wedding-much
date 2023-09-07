package me.janek.weddingmuch.domain

import me.janek.weddingmuch.api.CreateRequest
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.infrastructure.GiftRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class GiftServiceImplTest @Autowired constructor (
  private val giftService: GiftService,
  private val giftRepository: GiftRepository
) {

  @Test
  fun `정상적으로 Gift를 저장한다`() {
    // given
    val request = CreateRequest(name = "Janek", price = 10_000, memo = null)

    // when
    giftService.saveNewGift(request)

    // then
    val allGift = giftService.getGiftList(PageCond())
    assertEquals(allGift[0].name, "Janek")
  }

}