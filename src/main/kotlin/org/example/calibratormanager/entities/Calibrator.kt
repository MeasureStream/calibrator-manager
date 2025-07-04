package org.example.calibratormanager.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotBlank
import org.example.calibratormanager.DTOs.CalibrationUnitDTO
import org.springframework.data.geo.Point


@Entity
class Calibrator {

    @Id
    var networkId: Long = 0
    var networkIdMu: Long = 0  //Id Mu under calibration

    lateinit var name: String // Alternativa sono le string? - nullabili

    @OneToMany(cascade = [(CascadeType.ALL)], mappedBy = "calibrator" , orphanRemoval = true)
    lateinit var CalibrationUnits : MutableSet<CalibrationUnit>

    lateinit var location : Point



}