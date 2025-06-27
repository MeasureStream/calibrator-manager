package org.example.calibratormanager.services

import org.example.calibratormanager.DTOs.CalibratorDTO
import org.springframework.stereotype.Service


interface CalibratorService {
    fun getCalibrator(id:Long): CalibratorDTO?
    fun getAllCalibrator(): List<CalibratorDTO>
    fun createCalibrator(c: CalibratorDTO): CalibratorDTO
    fun deleteCalibrator(id: Long)
    fun updateCalibrator(c: CalibratorDTO): CalibratorDTO
    fun startCalibration(id: Long, type: String)
    fun stopCalibration(id: Long)

}