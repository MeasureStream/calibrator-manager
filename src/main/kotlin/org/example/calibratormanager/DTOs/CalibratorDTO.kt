package org.example.calibratormanager.DTOs

import org.example.calibratormanager.entities.CalibrationUnit
import org.example.calibratormanager.entities.Calibrator
import org.springframework.data.geo.Point

data class CalibratorDTO(
    val networkId: Long,
    val networkIdMu: Long,
    val name: String,
    val calibrationUnitsId: List<Long>,

    val location: Point
)


fun Calibrator.toDTO() = CalibratorDTO(networkId, networkIdMu, name, CalibrationUnits.map { it -> it.networkId }.toList(), location)