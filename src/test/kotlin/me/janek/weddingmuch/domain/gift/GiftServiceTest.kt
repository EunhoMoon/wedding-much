package me.janek.weddingmuch.domain.gift

import me.janek.weddingmuch.IntegrationTestSupport
import me.janek.weddingmuch.api.gift.GiftCreateRequest
import me.janek.weddingmuch.api.gift.GiftUpdateRequest
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.infrastructure.gift.GiftRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@SpringBootTest
class GiftServiceTest @Autowired constructor(
  private val giftService: GiftService,
  private val giftRepository: GiftRepository
) : IntegrationTestSupport() {

  @Test
  @DisplayName("축의금 내역 저장이 정상 작동한다.")
  fun save() {
    // given
    val request = GiftCreateRequest(name = "Janek", price = 10_000, memo = null)

    // when
    giftService.saveNewGift(request)

    // then
    assertThat(giftRepository.findAllGifts(PageCond())).hasSize(1)
      .extracting("name", "price")
      .containsExactlyInAnyOrder(
        tuple("Janek", 10_000)
      )
  }

  @Test
  @DisplayName("축의금 내역이 정상 수정된다.")
  fun update() {
    // given
    val newGift = Gift(name = "테스터", memo = null, price = 100_000, token = UUID.randomUUID().toString())
    giftRepository.save(newGift)
    val giftUpdateRequest = GiftUpdateRequest(newGift.token, name = "김테스", price = 100_000, memo = "수정")

    // when
    giftService.updateGift(giftUpdateRequest)

    //then
    val findGift = giftRepository.findByToken(newGift.token)
    assertThat(findGift).extracting("name", "memo")
      .containsExactlyInAnyOrder("김테스", "수정")
  }

  @Test
  @DisplayName("수정하고자 하는 축의금 내역을 찾을 수 없을 경우 예외 발생")
  fun updateFail() {
    // given
    val newGift = Gift(name = "테스터", memo = null, price = 100_000, token = UUID.randomUUID().toString())
    giftRepository.save(newGift)
    val giftUpdateRequest = GiftUpdateRequest(token = "not_exist_token", name = "김테스", price = 100_000, memo = "수정")

    // expect
    assertThatThrownBy { giftService.updateGift(giftUpdateRequest) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("Gift not found with token: not_exist_token")
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

  @Test
  @DisplayName("전체 축의 내역을 불러온다.")
  fun getAllGifts() {
    // given
    val newGift1 = Gift(name = "테스터 김", memo = null, price = 100_000, token = UUID.randomUUID().toString())
    val newGift2 = Gift(name = "테스터 이", memo = null, price = 30_000, token = UUID.randomUUID().toString())
    val newGift3 = Gift(name = "테스터 박", memo = null, price = 50_000, token = UUID.randomUUID().toString())
    giftRepository.saveAll(listOf(newGift1, newGift2, newGift3))

    // when
    val allGifts = giftService.getAllGifts(PageCond())

    //then
    assertThat(allGifts.totalPrice).isEqualTo(180_000)
    assertThat(allGifts.list).hasSize(3)
      .extracting("name", "price")
      .containsExactlyInAnyOrder(
        tuple("테스터 김", 100_000),
        tuple("테스터 이", 30_000),
        tuple("테스터 박", 50_000)
      )
  }

}
