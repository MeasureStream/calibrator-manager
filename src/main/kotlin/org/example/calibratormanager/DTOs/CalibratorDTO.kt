package org.example.calibratormanager.DTOs

import org.example.calibratormanager.entities.CalibrationUnit
import org.example.calibratormanager.entities.Calibrator

data class CalibratorDTO(
    val networkId: Long,
    val networkIdMu: Long,
    val name: String,
    val calibrationUnitsId: List<Long>,
)


fun Calibrator.toDTO() = CalibratorDTO(networkId, networkIdMu, name, CalibrationUnits.map { networkId })