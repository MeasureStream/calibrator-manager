package org.example.calibratormanager.controllers

import jakarta.validation.Valid
import org.example.calibratormanager.DTOs.CalibratorDTO
import org.example.calibratormanager.services.CalibratorService
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
@RequestMapping("/API/calibrator") //ascolta le richieste che hanno questo url aggiuntivop
class CalibratorController(private val cs: CalibratorService) {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createCalibrator(@Valid @RequestBody cal : CalibratorDTO): CalibratorDTO {
        return cs.createCalibrator(cal)
    }
    @GetMapping("/{networkId}")
    fun get(@PathVariable networkId: Long):CalibratorDTO? {
        return cs.getCalibrator(networkId)
    }
    @GetMapping()
    fun getAll():List<CalibratorDTO>{
        return cs.getAllCalibrator()
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping()
    fun update(@RequestBody cal : CalibratorDTO):CalibratorDTO {
        return cs.updateCalibrator(cal)
    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{networkId}")
    fun delete(@PathVariable networkId: Long){
        cs.deleteCalibrator(networkId)
    }

}