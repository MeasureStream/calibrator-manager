package org.example.calibratormanager.configurations

import jakarta.annotation.PostConstruct
import org.example.calibratormanager.entities.CalibrationUnit
import org.example.calibratormanager.entities.Calibrator
import org.example.calibratormanager.repositories.CalibrationUnitRepository
import org.example.calibratormanager.repositories.CalibratorRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.data.geo.Point
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class DataInitializer(
    val cur: CalibrationUnitRepository,
    val cr: CalibratorRepository,
) : InitializingBean {
    @PostConstruct
    fun init(){
        print("initialize fake data")
    }

    override fun afterPropertiesSet() {
        // Crea il Calibrator
        val calibrator = Calibrator().apply {
            networkId = 1L
            networkIdMu = 1001L
            name = "Calibrator A"
            CalibrationUnits = mutableSetOf() // inizializza l'insieme
            location = Point(Random.nextDouble(45.06,45.08), Random.nextDouble(7.5,7.6))
        }

        // Crea 4 CalibrationUnit e collegale al Calibrator
        val units = (1..4).map { i ->
            CalibrationUnit().apply {
                networkId = i.toLong()
                testPoint = i * 1.5
                measuresUnit = "unit-$i"
                type =  listOf("Temperature", "Pressure", "Humidity").random()
                this.calibrator = calibrator
            }
        }

        // Aggiungi le unità al calibratore
        calibrator.CalibrationUnits.addAll(units)

        // Salva tutto (grazie a CascadeType.ALL, basta salvare il calibratore)
        cr.save(calibrator)
    }
}