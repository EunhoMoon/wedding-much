package me.janek.weddingmuch.api.gift

import com.fasterxml.jackson.databind.ObjectMapper
import me.janek.weddingmuch.domain.gift.GiftService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(controllers = arrayOf(GiftController::class))
class GiftControllerTest @Autowired constructor(
  private val objectMapper: ObjectMapper,
  private val mockMvc: MockMvc,
  @MockBean private val giftService: GiftService
) {

  @Test
  @DisplayName("")
  fun saveGift() {
    // given
    val request = GiftCreateRequest(name = "테스터", price = 100_000, memo = null)

    // expect
    mockMvc.post("/api/gifts") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isCreated() } }

  }

}