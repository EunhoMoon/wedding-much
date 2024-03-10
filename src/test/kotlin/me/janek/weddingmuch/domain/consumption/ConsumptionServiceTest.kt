package me.janek.weddingmuch.domain.consumption

import me.janek.weddingmuch.IntegrationTestSupport
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.api.consumption.ConsumptionCreateRequest
import me.janek.weddingmuch.infrastructure.consumption.ConsumptionRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class ConsumptionServiceTest @Autowired constructor(
  private val consumptionService: ConsumptionService,
  private val consumptionRepository: ConsumptionRepository
) : IntegrationTestSupport() {

  @Test
  @DisplayName("소비 내역 저장이 정상 작동한다.")
  fun saveNewConsumption() {
    // given
    val request = ConsumptionCreateRequest("스드메", 3000_000, "스드메 비용")

    // when
    consumptionService.saveNewConsumption(request)

    //then
    assertThat(consumptionRepository.findAllConsumptions(PageCond())).hasSize(1)
      .extracting("place", "price")
      .containsExactlyInAnyOrder(
        tuple("스드메", 3000_000)
      )
  }

  @Test
  @DisplayName("")
  fun getConsumptionList() {
    // given
    val consumption1 = ConsumptionCreateRequest("스드메", 3000_000, "스드메 비용")
    val consumption2 = ConsumptionCreateRequest("웨딩링", 3000_000, "스드메 비용")
    val consumption3 = ConsumptionCreateRequest("항공권", 2500_000, "스드메 비용")
    consumptionRepository.saveAll(
      listOf(
        Consumption.of(consumption1),
        Consumption.of(consumption2),
        Consumption.of(consumption3)
      )
    )

    // when
    val consumptionList = consumptionService.getAllConsumption(PageCond())

    //then
    assertThat(consumptionList.total).isEqualTo(3)
    assertThat(consumptionList.totalPrice).isEqualTo(8500_000)
    assertThat(consumptionList.list).hasSize(3)
      .extracting("place", "price")
      .containsExactlyInAnyOrder(
        tuple("스드메", 3000_000),
        tuple("웨딩링", 3000_000),
        tuple("항공권", 2500_000)
      )
  }

}