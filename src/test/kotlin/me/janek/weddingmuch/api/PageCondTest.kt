package me.janek.weddingmuch.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PageCondTest {

  @Test
  fun `page, size가 null일 때 기본값으로 생성되어야 한다`() {
      // given
      val pageCond = PageCond();

      // expect
      assertEquals(pageCond.page, 1)
      assertEquals(pageCond.size, 10)
  }

}