package org.example.calibratormanager.services

import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.example.calibratormanager.DTOs.CalibratorDTO
import org.example.calibratormanager.DTOs.toDTO
import org.example.calibratormanager.entities.Calibrator
import org.example.calibratormanager.repositories.CalibratorRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull
@Service
class CalibratorServiceImpl(private val cr: CalibratorRepository): CalibratorService {
    override fun getCalibrator(id: Long): CalibratorDTO? {
        return cr.findById(id).getOrNull()?.toDTO() // toDTO è applicato solo nel caso non sia ritornato null (?)
    }

    override fun getAllCalibrator(): List<CalibratorDTO> {
        return cr.findAll().map{ it.toDTO() }
    }

    override fun createCalibrator(c: CalibratorDTO): CalibratorDTO {
        if(cr.findById(c.networkId).isPresent ) throw EntityExistsException()
        val calib= Calibrator().apply{
            networkId = c.networkId
            networkIdMu=c.networkIdMu
            type=c.type
            name=c.name
            measuresUnit=c.measuresUnit
            tempPoint=c.tempPoint
        }

        cr.save(calib)

        return calib.toDTO()
    }

    override fun deleteCalibrator(id: Long) {
        cr.deleteById(id)
    }

    override fun updateCalibrator(c: CalibratorDTO): CalibratorDTO {
        if(cr.findById(c.networkId).isEmpty ) throw EntityNotFoundException()
        val calib = cr.findById(c.networkId).get()
        calib.apply{
            networkId = c.networkId
            networkIdMu=c.networkIdMu
            type=c.type
            name=c.name
            measuresUnit=c.measuresUnit
            tempPoint=c.tempPoint
        }

        cr.save(calib)

        return calib.toDTO()
    }

    override fun startCalibration(id: Long, type: String) {
        TODO("Not yet implemented")
    }

    override fun stopCalibration(id: Long) {
        TODO("Not yet implemented")
    }

}