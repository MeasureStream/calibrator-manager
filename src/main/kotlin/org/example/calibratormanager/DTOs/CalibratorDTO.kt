package org.example.calibratormanager.DTOs

import org.example.calibratormanager.entities.Calibrator

data class CalibratorDTO(
    val networkId: Long,
    val networkIdMu: Long,
    val measuresUnit: String,
    val type: String,
    val name: String,
    val tempPoint: Double,
)


fun Calibrator.toDTO() = CalibratorDTO(networkId, networkIdMu, measuresUnit, type, name, tempPoint)