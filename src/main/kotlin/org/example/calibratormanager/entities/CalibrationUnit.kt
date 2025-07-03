package org.example.calibratormanager.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank

@Entity
class CalibrationUnit {
    @Id
    var networkId : Long=0

    var testPoint: Double = 0.0 // Le variabili numeriche vanno inizializzate

    @NotBlank
    lateinit var measuresUnit : String
    @NotBlank
    lateinit var type: String // lateinit aka inizializzato più avanti (runtime)

    @ManyToOne(cascade = [(CascadeType.ALL)])
    var calibrator : Calibrator? = null

}