package me.janek.weddingmuch.api.consumption

import com.fasterxml.jackson.databind.ObjectMapper
import me.janek.weddingmuch.api.PageCond
import me.janek.weddingmuch.domain.consumption.ConsumptionService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@WebMvcTest(controllers = arrayOf(ConsumptionController::class))
class ConsumptionControllerTest @Autowired constructor(
  private val objectMapper: ObjectMapper,
  private val mockMvc: MockMvc,
  @MockBean private val consumptionService: ConsumptionService
) {

  @Test
  @DisplayName("신규 소비 내역을 등록한다.")
  fun saveConsumption() {
    // given
    val request = ConsumptionCreateRequest(place = "스드메", price = 3_000_000, memo = null)

    // expect
    mockMvc.post("/api/consumptions") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isCreated() } }
  }

  @Test
  @DisplayName("신규 소비 등록시 내역은 필수다.")
  fun saveConsumptionFailWhenPlaceIsEmpty() {
    // given
    val request = ConsumptionCreateRequest(place = "", price = 3_000_000, memo = null)

    // expect
    mockMvc.post("/api/consumptions") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isBadRequest() } }
      .andExpect { jsonPath("$.place") { value("소비 내역은 필수입니다.") } }
  }

  @Test
  @DisplayName("신규 소비 등록시 금액은 필수다.")
  fun saveConsumptionFailWhenPriceIsNull() {
    // given
    val request = ConsumptionCreateRequest(place = "스드메", price = null, memo = null)

    // expect
    mockMvc.post("/api/consumptions") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isBadRequest() } }
      .andExpect { jsonPath("$.price") { value("소비 금액은 필수입니다.") } }
  }

  @Test
  @DisplayName("기존 소비 내역을 수정한다.")
  fun updateConsumption() {
    // given
    val request = ConsumptionUpdateRequest(token = "token", place = "스드메", price = 3_000_000, memo = null)

    // expect
    mockMvc.put("/api/consumptions") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isOk() } }
  }

  @Test
  @DisplayName("기존 소비 내역 수정시 토큰은 필수다.")
  fun updateConsumptionFailWhenTokenIsEmpty() {
    // given
    val request = ConsumptionUpdateRequest(token = "", place = "스드메", price = 3_000_000, memo = null)

    // expect
    mockMvc.put("/api/consumptions") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isBadRequest() } }
      .andExpect { jsonPath("$.token") { value("토큰은 필수입니다.") } }
  }

  @Test
  @DisplayName("기존 소비 내역 수정시 내역은 필수다.")
  fun updateConsumptionFailWhenPlaceIsEmpty() {
    // given
    val request = ConsumptionUpdateRequest(token = "token", place = "", price = 3_000_000, memo = null)

    // expect
    mockMvc.put("/api/consumptions") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isBadRequest() } }
      .andExpect { jsonPath("$.place") { value("소비 내역은 필수입니다.") } }
  }

  @Test
  @DisplayName("기존 소비 내역 수정시 금액은 필수다.")
  fun updateConsumptionFailWhenPriceIsNull() {
    // given
    val request = ConsumptionUpdateRequest(token = "token", place = "스드메", price = null, memo = null)

    // expect
    mockMvc.put("/api/consumptions") {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsBytes(request)
    }
      .andDo { print() }
      .andExpect { status { isBadRequest() } }
      .andExpect { jsonPath("$.price") { value("소비 금액은 필수입니다.") } }
  }

  @Test
  @DisplayName("전체 소비 내역을 조회한다.")
  fun getAllConsumptions() {
    // given
    val result = ConsumptionPageable(
      total = 1,
      pageCond = PageCond(),
      list = listOf(ConsumptionInfoResponse(place = "스드메", price = 3_000_000, memo = null, token = "token"))
    )
    Mockito.`when`(consumptionService.getAllConsumption(PageCond())).thenReturn(result)

    // expect
    mockMvc.get("/api/consumptions")
      .andDo { print() }
      .andExpect { status { isOk() } }
      .andExpect { jsonPath("$.total") { value(1) } }
      .andExpect { jsonPath("$.totalPrice") { value(3_000_000) } }
      .andExpect { jsonPath("$.list") { isArray() } }
      .andExpect { jsonPath("$.pageCount") { value(1) } }
      .andExpect { jsonPath("$.presentPages") { value(1) } }
  }

  @Test
  @DisplayName("")
  fun deleteConsumption() {
    // given
    val consumptionToken = "token"

    // expect
    mockMvc.delete("/api/consumptions/$consumptionToken")
      .andDo { print() }
      .andExpect { status { isOk() } }
  }

}