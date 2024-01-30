package me.janek.weddingmuch.api

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PageCondTest {

  @Test
  @DisplayName("page, size가 null일 때 기본값으로 생성되어야 한다.")
  fun defaultCreate() {
    // given
    val pageCond = PageCond();

    // expect
    assertThat(pageCond.page).isEqualTo(1)
    assertThat(pageCond.size).isEqualTo(10)
  }

}