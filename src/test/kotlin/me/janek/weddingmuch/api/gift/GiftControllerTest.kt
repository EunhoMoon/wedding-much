package me.janek.weddingmuch.api.gift

import com.fasterxml.jackson.databind.ObjectMapper
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.domain.gift.GiftService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put
import java.util.UUID

@WebMvcTest(controllers = arrayOf(GiftController::class))
class GiftControllerTest @Autowired constructor(
  private val objectMapper: ObjectMapper,
  private val mockMvc: MockMvc,
  @MockBean private val giftService: GiftService
) {

  @Test
  @DisplayName("신규 축의 내역을 등록한다.")
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

  @Test
  @DisplayName("신구 축의 등록시 이름은 필수다.")
  fun saveGiftFailWhenNameIsEmpty() {
    // given
    val request = GiftCreateRequest(name = "", price = 100_000, memo = null)

    // expect
    mockMvc.post("/api/gifts") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isBadRequest() } }
      .andExpect { jsonPath("$.name") { value("이름은 필수입니다.") } }
  }

  @Test
  @DisplayName("신구 축의 등록시 가격은 필수다.")
  fun saveGiftFailWhenPriceIsNull() {
    // given
    val request = GiftCreateRequest(name = "테스터", price = null, memo = null)

    // expect
    mockMvc.post("/api/gifts") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isBadRequest() } }
      .andExpect { jsonPath("$.price") { value("가격은 필수입니다.") } }
  }

  @Test
  @DisplayName("기존 축의 내역을 수정한다.")
  fun updateGift() {
    // given
    val request =
      GiftUpdateRequest(token = UUID.randomUUID().toString(), name = "테스터", price = 100_000, memo = "수정")

    // expect
    mockMvc.put("/api/gifts") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isOk() } }
  }

  @Test
  @DisplayName("기존 축의 내역 수정시 토큰은 필수다.")
  fun updateGiftFailWhenTokenIsEmpty() {
    // given
    val request = GiftUpdateRequest(token = "", name = "테스터", price = 100_000, memo = null)

    // expect
    mockMvc.put("/api/gifts") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isBadRequest() } }
      .andExpect { jsonPath("$.token") { value("토큰은 필수입니다.") } }
  }

  @Test
  @DisplayName("기존 축의 내역 수정시 이름은 필수다.")
  fun updateGiftFailWhenNameIsEmpty() {
    // given
    val request = GiftUpdateRequest(token = "token-001", name = "", price = 100_000, memo = null)

    // expect
    mockMvc.put("/api/gifts") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isBadRequest() } }
      .andExpect { jsonPath("$.name") { value("이름은 필수입니다.") } }
  }

  @Test
  @DisplayName("기존 축의 내역 수정시 가격은 필수다.")
  fun updateGiftFailWhenPriceIsNull() {
    // given
    val request = GiftUpdateRequest(token = "token-001", name = "테스터", price = null, memo = null)

    // expect
    mockMvc.put("/api/gifts") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isBadRequest() } }
      .andExpect { jsonPath("$.price") { value("가격은 필수입니다.") } }
  }

  @Test
  @DisplayName("전체 축의 내역을 조회한다.")
  fun getAllGifts() {
    // given
    val result = GiftPageable(total = 0, pageCond = PageCond(), list = emptyList())
    Mockito.`when`(giftService.getAllGifts(PageCond())).thenReturn(result)

    // expect
    mockMvc.get("/api/gifts")
      .andDo { print() }
      .andExpect { status { isOk() } }
      .andExpect { jsonPath("$.total") { value(0) } }
      .andExpect { jsonPath("$.list") { isArray() } }
      .andExpect { jsonPath("$.pageCount") { value(1) } }
      .andExpect { jsonPath("$.presentPages") { value(1) } }
      .andExpect { jsonPath("$.totalPrice") { value(0) } }
  }

}