package me.janek.weddingmuch.domain.consumption

import me.janek.weddingmuch.IntegrationTestSupport
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.api.consumption.ConsumptionCreateRequest
import me.janek.weddingmuch.api.consumption.ConsumptionUpdateRequest
import me.janek.weddingmuch.infrastructure.consumption.ConsumptionRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

@Transactional
class ConsumptionServiceTest @Autowired constructor(
  private val consumptionService: ConsumptionService,
  private val consumptionRepository: ConsumptionRepository
) : IntegrationTestSupport() {

  @Test
  @DisplayName("소비 내역 저장이 정상 작동한다.")
  fun saveNewConsumption() {
    // given
    val request = ConsumptionCreateRequest("스드메", 3_000_000, "스드메 비용")

    // when
    consumptionService.saveNewConsumption(request)

    //then
    assertThat(consumptionRepository.findAllConsumptions(PageCond())).hasSize(1)
      .extracting("place", "price")
      .containsExactlyInAnyOrder(
        tuple("스드메", 3_000_000)
      )
  }

  @Test
  @DisplayName("소비 내역이 정상 수정된다.")
  fun updateConsumption() {
    // given
    val newConsumption = Consumption.of(ConsumptionCreateRequest("스드메", 3_000_000, "스드메 비용"), userToken ="1")
    consumptionRepository.save(newConsumption)
    val consumptionUpdateRequest =
      ConsumptionUpdateRequest(newConsumption.token, place = "스드메", price = 3_500_000, memo = "스드메 비용")

    // when
    consumptionService.updateConsumption(consumptionUpdateRequest)

    //then
    val findConsumption = consumptionRepository.findByToken(newConsumption.token)
    assertThat(findConsumption).extracting("place", "price", "memo")
      .containsExactlyInAnyOrder("스드메", 3_500_000, "스드메 비용")
  }

  @Test
  @DisplayName("수정하고자 하는 소비 내역을 찾을 수 없을 경우 예외 발생")
  fun updateConsumptionFailWhenTokenIsNotExists() {
    // given
    val newConsumption = Consumption.of(ConsumptionCreateRequest("스드메", 3_000_000, "스드메 비용"), userToken ="1")
    consumptionRepository.save(newConsumption)
    val consumptionUpdateRequest =
      ConsumptionUpdateRequest(token = "not_exist_token", place = "스드메", price = 3_500_000, memo = "스드메 비용")

    // expect
    assertThatThrownBy { consumptionService.updateConsumption(consumptionUpdateRequest) }
      .isInstanceOf(IllegalArgumentException::class.java)
      .hasMessage("Consumption not found with token: not_exist_token")
  }

  @Test
  @DisplayName("전체 소비 내역을 불러온다.")
  fun getConsumptionList() {
    // given
    val consumption1 = ConsumptionCreateRequest("스드메", 3_000_000, "스드메 비용")
    val consumption2 = ConsumptionCreateRequest("웨딩링", 3_000_000, "웨딩링 비용")
    val consumption3 = ConsumptionCreateRequest("항공권", 2_500_000, "항공권 비용")
    consumptionRepository.saveAll(
      listOf(
        Consumption.of(consumption1, userToken ="1"),
        Consumption.of(consumption2, userToken ="1"),
        Consumption.of(consumption3, userToken ="1")
      )
    )

    // when
    val consumptionList = consumptionService.getAllConsumption(PageCond())

    //then
    assertThat(consumptionList.total).isEqualTo(3)
    assertThat(consumptionList.totalPrice).isEqualTo(8_500_000)
    assertThat(consumptionList.list).hasSize(3)
      .extracting("place", "price")
      .containsExactlyInAnyOrder(
        tuple("스드메", 3_000_000),
        tuple("웨딩링", 3_000_000),
        tuple("항공권", 2_500_000)
      )
  }

}