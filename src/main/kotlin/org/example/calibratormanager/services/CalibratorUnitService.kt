package org.example.calibratormanager.services

import org.example.calibratormanager.DTOs.CalibrationUnitDTO

interface CalibrationUnitService {

    fun getCalibrationUnit(id:Long): CalibrationUnitDTO?
    fun getAllCalibrationUnit(): List<CalibrationUnitDTO>
    fun createCalibrationUnit(c: CalibrationUnitDTO): CalibrationUnitDTO
    fun deleteCalibrationUnit(id: Long)
    fun updateCalibrationUnit(c: CalibrationUnitDTO): CalibrationUnitDTO
    fun startCalibration(id: Long, type: String)
    fun stopCalibration(id: Long)
}