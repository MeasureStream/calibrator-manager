package org.example.calibratormanager

import com.fasterxml.jackson.databind.ObjectMapper
import com.nimbusds.jose.shaded.gson.Gson
import org.example.calibratormanager.DTOs.CalibrationUnitDTO
import org.example.calibratormanager.DTOs.CalibratorDTO
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.annotation.Order
import org.springframework.test.web.servlet.MockMvc
import kotlin.test.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import kotlin.random.Random


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalibrationUnitControllerTest @Autowired constructor(val mockMvc: MockMvc){
    companion object{
        @JvmStatic
        var setup = false
    }


    val baseUrl = "/API/"
    val calibrationUnitsUrl = "/API/calibrationunits"
    val calibratorUrl = "/API/calibrator"




    val calibrationUnits = List(20) {
        CalibrationUnitDTO(
            networkId = 1000L + it,
            testPoint = Random.nextDouble(0.0, 100.0),
            measuresUnit = listOf("°C", "kg", "m", "Pa", "V").random(),
            type = listOf("Temperature", "Pressure", "Weight", "Length", "Voltage").random(),
            calibratorId =  1
        )
    }

    val calibrators = List(1) { index ->
        val calibrationUnitIds = calibrationUnits
            .filter { it.calibratorId == (index + 1).toLong() }
            .map { it.networkId }

        CalibratorDTO(
            networkId = (index + 20).toLong(),
            networkIdMu = (index + 1).toLong() + 200,
            name = "Calibrator-${index + 1}",
            calibrationUnitsId = listOf()
        )
    }





    fun upload_CU(cu: CalibrationUnitDTO, httpStatusCode : ResultMatcher): MvcResult {


        val response = mockMvc.perform(
            MockMvcRequestBuilders
                .post(calibrationUnitsUrl)
                .content(Gson().toJson(cu))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(httpStatusCode)
        //.andExpect(MockMvcResultMatchers.content().json(Gson().toJson(c)))

        return response.andReturn();
    }


    fun update_CU(cu: CalibrationUnitDTO, httpStatusCode : ResultMatcher ): MvcResult {


        val response = mockMvc.perform(
            MockMvcRequestBuilders
                .put(calibrationUnitsUrl)
                .content(Gson().toJson(cu))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(httpStatusCode)
        //.andExpect(MockMvcResultMatchers.content().json(Gson().toJson(c)))

        return response.andReturn();
    }

    fun delete_CU(cu: CalibrationUnitDTO, httpStatusCode : ResultMatcher ): MvcResult {


        val response = mockMvc.perform(
            MockMvcRequestBuilders
                .delete(calibrationUnitsUrl)
                .content(Gson().toJson(cu))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(httpStatusCode)
        //.andExpect(MockMvcResultMatchers.content().json(Gson().toJson(c)))

        return response.andReturn();
    }

    fun upload_Calibrator(n : CalibratorDTO ): MvcResult {


        val response = mockMvc.perform(
            MockMvcRequestBuilders
                .post(calibratorUrl)
                .content(Gson().toJson(n))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated())
        //.andExpect(MockMvcResultMatchers.content().json(Gson().toJson(c)))

        return response.andReturn();
    }


    @Test
    @BeforeAll
    fun init(): Unit {
        if(!setup) {
            calibrators.forEach { upload_Calibrator(it) }
            calibrationUnits.forEach { upload_CU(it, status().isCreated) }


            setup = true;
        }
    }

    @Test
    fun testGetCalibrationUnitById() {
        val unit = calibrationUnits.first()

        val response = mockMvc.perform(
            get("$calibrationUnitsUrl/${unit.networkId}")
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.networkId").value(unit.networkId))
            .andReturn()
    }

    @Test
    fun testCreateCalibrationUnit() {
        val newCU = CalibrationUnitDTO(
            networkId = 9999L,
            testPoint = 42.0,
            measuresUnit = "kg",
            type = "Weight",
            calibratorId = 1L
        )

        upload_CU(newCU, status().isCreated)
    }

    @Test
    fun testUpdateCalibrationUnit() {
        val unitToUpdate = calibrationUnits[0].copy(testPoint = 77.7)

        update_CU(unitToUpdate, status().isOk)
    }

    @Test
    fun testDeleteCalibrationUnit() {
        val unitToDelete = CalibrationUnitDTO(
            networkId = 8888L,
            testPoint = 55.0,
            measuresUnit = "m",
            type = "Length",
            calibratorId = 2L
        )

        // Prima la creo
        upload_CU(unitToDelete, status().isCreated)

        // Poi la elimino
        delete_CU(unitToDelete, status().isOk)
    }




}