package org.example.calibratormanager.services

import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.example.calibratormanager.DTOs.CalibratorDTO
import org.example.calibratormanager.DTOs.toDTO
import org.example.calibratormanager.entities.Calibrator
import org.example.calibratormanager.repositories.CalibrationUnitRepository
import org.example.calibratormanager.repositories.CalibratorRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull
@Service
class CalibratorServiceImpl(private val cr: CalibratorRepository, private val cur: CalibrationUnitRepository): CalibratorService {
    override fun getCalibrator(id: Long): CalibratorDTO? {
        return cr.findById(id).getOrNull()?.toDTO() // toDTO è applicato solo nel caso non sia ritornato null (?)
    }

    override fun getAllCalibrator(): List<CalibratorDTO> {
        return cr.findAll().map{ it.toDTO() }
    }

    override fun createCalibrator(c: CalibratorDTO): CalibratorDTO {
        if(cr.findById(c.networkId).isPresent ) throw EntityExistsException()
        val cus =  cur.findAllById( c.calibrationUnitsId)


        val calib= Calibrator().apply{
            networkId = c.networkId
            networkIdMu=c.networkIdMu
            name=c.name
            CalibrationUnits = mutableSetOf()
            location = c.location
        }


        if(c.calibrationUnitsId.isNotEmpty()){

            if(cus.any { it.calibrator != null && it.calibrator!!.networkId != calib.networkId })
                throw Exception("A CalibratorUnit is already assigned ${cus.filter { it.calibrator != null && it.calibrator!!.networkId != calib.networkId  }}")
            cus.forEach { it.calibrator = calib }
            cur.saveAll(cus)
            calib.CalibrationUnits = cus.toMutableSet()
        }

        return cr.save(calib).toDTO()
    }

    override fun deleteCalibrator(id: Long) {
        cr.deleteById(id)
    }

    override fun updateCalibrator(c: CalibratorDTO): CalibratorDTO {

        val cus =  cur.findAllById( c.calibrationUnitsId)
        val oldCalibrator = cr.findById(c.networkId).get()

        val calib= oldCalibrator.apply{
            networkId = c.networkId
            networkIdMu=c.networkIdMu
            name=c.name
            location = c.location
        }


        if(c.calibrationUnitsId.isNotEmpty()){

            if(cus.any { it.calibrator != null && it.calibrator!!.networkId != calib.networkId })
                throw Exception("A CalibratorUnit is already assigned ${cus.filter { it.calibrator != null && it.calibrator!!.networkId != calib.networkId  }}")
            cus.forEach { it.calibrator = calib }
            cur.saveAll(cus)
            calib.CalibrationUnits = cus.toMutableSet()
        }

        return cr.save(calib).toDTO()
    }

    override fun startCalibration(id: Long, type: String) {
        TODO("Not yet implemented")
    }

    override fun stopCalibration(id: Long) {
        TODO("Not yet implemented")
    }

}