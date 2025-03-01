package me.janek.weddingmuch.domain.gift

import me.janek.weddingmuch.api.gift.GiftCreateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class GiftTest {

  @Test
  @DisplayName("축의금 내역 생성시 token을 정상 생성한다.")
  fun create() {
    // given
    val createRequest = GiftCreateRequest(name = "테스터", price = 100_000, memo = null)

    // when
    val newGift = Gift.of(createRequest, userToken ="1")

    //then
    assertThat(newGift.token).isNotBlank()
  }

}