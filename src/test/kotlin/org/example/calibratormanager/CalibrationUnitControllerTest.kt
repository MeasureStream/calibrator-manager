package org.example.calibratormanager

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.calibratormanager.DTOs.CalibrationUnitDTO
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.annotation.Order
import org.springframework.test.web.servlet.MockMvc
import kotlin.test.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@SpringBootTest
@AutoConfigureMockMvc
class CalibrationUnitControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    val baseUrl = "/API/calibrationunits"

    val exampleUnit = CalibrationUnitDTO(
        networkId = 9999L,
        testPoint = 42.0,
        measuresUnit = "Celsius",
        type = "Temperature",
        calibratorId = 1L
    )

    @Test
    @Order(1)
    fun `POST - create calibration unit`() {
        val json = objectMapper.writeValueAsString(exampleUnit)

        mockMvc.perform(
            post(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isCreated)
            .andExpect(jsonPath("$.networkId").value(9999L))
            .andExpect(jsonPath("$.type").value("Temperature"))
    }

    @Test
    @Order(2)
    fun `GET - retrieve calibration unit by networkId`() {
        mockMvc.perform(get("$baseUrl/9999"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.networkId").value(9999L))
            .andExpect(jsonPath("$.measuresUnit").value("Celsius"))
    }

    @Test
    @Order(3)
    fun `GET - retrieve all calibration units`() {
        mockMvc.perform(get(baseUrl))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$.length()").value(org.hamcrest.Matchers.greaterThanOrEqualTo(1)))
    }

    @Test
    @Order(4)
    fun `PUT - update existing calibration unit`() {
        val updated = exampleUnit.copy(testPoint = 99.9)
        val json = objectMapper.writeValueAsString(updated)

        mockMvc.perform(
            put(baseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isAccepted)
            .andExpect(jsonPath("$.testPoint").value(99.9))
    }

    @Test
    @Order(5)
    fun `DELETE - delete calibration unit`() {
        mockMvc.perform(delete("$baseUrl/9999"))
            .andExpect(status().isAccepted)

        mockMvc.perform(get("$baseUrl/9999"))
            .andExpect(status().isOk)
            .andExpect(content().string(""))
    }
}