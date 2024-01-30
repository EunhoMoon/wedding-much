package me.janek.weddingmuch.domain

import me.janek.weddingmuch.api.GiftCreateRequest
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.infrastructure.GiftRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class GiftServiceImplTest @Autowired constructor(
  private val giftService: GiftService,
  private val giftRepository: GiftRepository
) {

  @Test
  @DisplayName("축의금 내역 저장이 정상 작동한다.")
  fun save() {
    // given
    val request = GiftCreateRequest(name = "Janek", price = 10_000, memo = null)

    // when
    giftService.saveNewGift(request)

    // then
    assertThat(giftService.getGiftList(PageCond())[0].name).isEqualTo("Janek")
  }

  @Test
  @DisplayName("토큰을 통한 축의금 내역 삭제가 정상 작동한다.")
  fun delete() {
    // given
    val newGiftToken = Gift.of(GiftCreateRequest(name = "Janek", price = 100_000, memo = null)).token

    // when
    giftService.deleteGift(newGiftToken)

    //then
    assertThat(giftRepository.count()).isEqualTo(0)
  }

}
