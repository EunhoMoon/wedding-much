package me.janek.weddingmuch.infrastructure.consumption

import me.janek.weddingmuch.IntegrationTestSupport
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.domain.consumption.Consumption
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class ConsumptionRepositoryTest @Autowired constructor(
  private val consumptionRepository: ConsumptionRepository
) : IntegrationTestSupport() {

  @Test
  @DisplayName("전체 소비 내역 조회 및 페이징 처리, 정렬 기본값은 id 내림차순이다.")
  fun findAllConsumptions() {
    // given
    arrayOf(
      Consumption(token = "token1", place = "place1", memo = null, price = 1_000, userToken ="1"),
      Consumption(token = "token2", place = "place2", memo = "테스트", price = 2_000, userToken ="1"),
      Consumption(token = "token3", place = "place3", memo = null, price = 3_000, userToken ="1"),
    )
      .toList()
      .let { consumptionRepository.saveAll(it) }

    // when
    val consumptions = consumptionRepository.findAllConsumptions(PageCond(page = 1, size = 2))

    // then
    assertThat(consumptions).hasSize(2)
      .extracting("token", "place", "price", "memo")
      .containsExactlyInAnyOrder(
        tuple("token3", "place3", 3_000, null),
        tuple("token2", "place2", 2_000, "테스트")
      )
  }

  @Test
  @DisplayName("토큰 값을 통해 축의 내역을 조회한다.")
  fun findByToken() {
    // given
    Consumption(token = "token1", place = "place1", memo = null, price = 1_000, userToken ="1")
      .let { consumptionRepository.save(it) }

    // when
    val consumption = consumptionRepository.findByToken("token1")

    // then
    assertThat(consumption).isNotNull
      .extracting("token", "place", "price", "memo")
      .containsExactly("token1", "place1", 1_000, null)
  }

  @Test
  @DisplayName("토큰 값을 통해 축의 내역을 조회한다. 토큰이 없는 경우 null을 반환한다.")
  fun findByTokenWhenTokenIsNotExists() {
    // given
    val token = "not_exists_token"

    // when
    val consumption = consumptionRepository.findByToken(token)

    // then
    assertThat(consumption).isNull()
  }

  @Test
  @DisplayName("토큰 값을 통해 축의 내역을 삭제한다.")
  fun deleteByToken() {
    // given
    Consumption(token = "token1", place = "place1", memo = null, price = 1_000, userToken ="1")
      .let { consumptionRepository.save(it) }

    // when
    consumptionRepository.deleteByToken("token1")

    // then
    val consumption = consumptionRepository.findByToken("token1")
    assertThat(consumption).isNull()
  }


}