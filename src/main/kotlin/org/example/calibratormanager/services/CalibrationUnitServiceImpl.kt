package org.example.calibratormanager.services

import jakarta.persistence.EntityExistsException
import org.example.calibratormanager.DTOs.CalibrationUnitDTO
import org.example.calibratormanager.DTOs.toDTO
import org.example.calibratormanager.entities.CalibrationUnit
import org.example.calibratormanager.repositories.CalibrationUnitRepository
import org.example.calibratormanager.repositories.CalibratorRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CalibrationUnitServiceImpl(private val cur: CalibrationUnitRepository, private val cr: CalibratorRepository): CalibrationUnitService {
    override fun getCalibrationUnit(id: Long): CalibrationUnitDTO? {
        return cur.findByIdOrNull(id)?.toDTO()
    }

    override fun getAllCalibrationUnit(): List<CalibrationUnitDTO> {
        return cur.findAll().map { it.toDTO() }
    }

    override fun createCalibrationUnit(c: CalibrationUnitDTO): CalibrationUnitDTO {
        if(cur.findById(c.networkId).isPresent) throw EntityExistsException()


        val calibrationUnit = CalibrationUnit().apply {
            networkId = c.networkId
            testPoint = c.testPoint
            measuresUnit = c.measuresUnit
            type = c.type
        }

        if(c.calibratorId != null){
            val calib = cr.findById(c.calibratorId).get()
            calibrationUnit.calibrator = calib
            calib.CalibrationUnits.add(calibrationUnit)
            cr.save(calib)
        }

        return cur.save(calibrationUnit).toDTO()
    }

    override fun deleteCalibrationUnit(id: Long) {
        cur.deleteById(id)
    }

    override fun updateCalibrationUnit(c: CalibrationUnitDTO): CalibrationUnitDTO {
        val old = cur.findById(c.networkId).get()


        val calibrationUnit = old.apply {
            networkId = c.networkId
            testPoint = c.testPoint
            measuresUnit = c.measuresUnit
            type = c.type
        }

        when {
            c.calibratorId != null && old.calibrator == null -> {
                val calib = cr.findById(c.calibratorId).get()

                calibrationUnit.calibrator = calib
                calib.CalibrationUnits.add(calibrationUnit)

                cr.save(calib)
                return  cur.save(calibrationUnit).toDTO()
            }
            c.calibratorId == null  && old.calibrator != null ->{
                old.calibrator = null
                return  cur.save(calibrationUnit).toDTO()
            }
            c.calibratorId != null && old.calibrator != null ->{
                if (c.calibratorId != old.calibrator!!.networkId) {
                    val calib = cr.findById(c.calibratorId).get()
                    calibrationUnit.calibrator = calib
                    calib.CalibrationUnits.add(calibrationUnit)
                    return  cur.save(calibrationUnit).toDTO()
                }
            }
            else -> {
                return cur.save(calibrationUnit).toDTO()
            }
        }




    }

    override fun startCalibration(id: Long, type: String) {
        TODO("Not yet implemented")
    }

    override fun stopCalibration(id: Long) {
        TODO("Not yet implemented")
    }
}