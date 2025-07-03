package org.example.calibratormanager.DTOs

import org.example.calibratormanager.entities.CalibrationUnit

data class CalibrationUnitDTO(
    val networkId : Long,
    val testPoint: Double,
    val measuresUnit : String,
    val type: String,
    val calibratorId: Long?,
)

fun CalibrationUnit.toDTO() : CalibrationUnitDTO = CalibrationUnitDTO(networkId, testPoint, measuresUnit, type,
    calibrator?.networkId
)
