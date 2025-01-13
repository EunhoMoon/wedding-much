package me.janek.weddingmuch.infrastructure.gift

import me.janek.weddingmuch.IntegrationTestSupport
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.domain.gift.Gift
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class GiftRepositoryTest @Autowired constructor(
  private val giftRepository: GiftRepository
) : IntegrationTestSupport() {

  @Test
  @DisplayName("전체 축의 내역 조회 및 페이징 처리, 정렬 기본값은 id 내림차순이다.")
  fun findAllGifts() {
    // given
    arrayOf(
      Gift(token = "token1", name = "name1", memo = null, price = 1_000, userToken ="1"),
      Gift(token = "token2", name = "name2", memo = null, price = 2_000, userToken ="1"),
      Gift(token = "token3", name = "name3", memo = "테스트", price = 3_000, userToken ="1"),
    )
      .toList()
      .let { giftRepository.saveAll(it) }

    // when
    val gifts = giftRepository.findAllGifts(PageCond(page = 1, size = 2))

    // then
    assertThat(gifts).hasSize(2)
      .extracting("token", "name", "price", "memo")
      .containsExactlyInAnyOrder(
        tuple("token3", "name3", 3_000, "테스트"),
        tuple("token2", "name2", 2_000, null)
      )
  }

  @Test
  @DisplayName("토큰 값을 통해 축의 내역을 조회한다.")
  fun findByToken() {
    // given
    Gift(token = "token1", name = "name1", memo = null, price = 1_000, userToken ="1")
      .let { giftRepository.save(it) }

    // when
    val gift = giftRepository.findByToken("token1")

    // then
    assertThat(gift).isNotNull
      .extracting("token", "name", "price", "memo")
      .containsExactly("token1", "name1", 1_000, null)
  }

  @Test
  @DisplayName("토큰 값을 통해 축의 내역을 조회한다. 토큰이 없는 경우 null을 반환한다.")
  fun findByTokenWhenTokenIsNotExists() {
    // given
    val token = "not_exists_token"

    // when
    val gift = giftRepository.findByToken(token)

    // then
    assertThat(gift).isNull()
  }

  @Test
  @DisplayName("토큰 값을 통해 축의 내역을 삭제한다.")
  fun deleteByToken() {
    // given
    Gift(token = "token1", name = "name1", memo = null, price = 1_000, userToken ="1")
      .let { giftRepository.save(it) }

    // when
    giftRepository.deleteByToken("token1")

    // then
    val gift = giftRepository.findByToken("token1")
    assertThat(gift).isNull()
  }

}