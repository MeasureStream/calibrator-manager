package org.example.calibratormanager.controllers

import jakarta.validation.Valid
import org.example.calibratormanager.DTOs.CalibrationUnitDTO
import org.example.calibratormanager.services.CalibrationUnitService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/API/calibrationunits")
class CalibrationUnitController(private val cs: CalibrationUnitService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createCalibrationUnit(@Valid @RequestBody cal : CalibrationUnitDTO): CalibrationUnitDTO {
        return cs.createCalibrationUnit(cal)
    }
    @GetMapping("/{networkId}")
    fun get(@PathVariable networkId: Long):CalibrationUnitDTO? {
        return cs.getCalibrationUnit(networkId)
    }
    @GetMapping()
    fun getAll():List<CalibrationUnitDTO>{
        return cs.getAllCalibrationUnit()
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping()
    fun update(@RequestBody cal : CalibrationUnitDTO):CalibrationUnitDTO {
        return cs.updateCalibrationUnit(cal)
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{networkId}")
    fun delete(@PathVariable networkId: Long){
        cs.deleteCalibrationUnit(networkId)
    }
}