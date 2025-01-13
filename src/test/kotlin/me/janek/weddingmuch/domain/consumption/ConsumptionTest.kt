package me.janek.weddingmuch.domain.consumption

import me.janek.weddingmuch.api.consumption.ConsumptionCreateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ConsumptionTest {

  @Test
  @DisplayName("소비 내역 생성시 token을 정상 생성한다.")
  fun create() {
    // given
    val createRequest = ConsumptionCreateRequest(place = "스드메", price = 3000_000, memo = "계좌이체")

    // when
    val newConsumption = Consumption.of(createRequest, userToken ="1")

    //then
    assertThat(newConsumption.token).isNotBlank()
  }

}