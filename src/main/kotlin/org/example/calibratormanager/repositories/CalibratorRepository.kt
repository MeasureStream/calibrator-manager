package org.example.calibratormanager.repositories

import org.example.calibratormanager.entities.Calibrator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CalibratorRepository: JpaRepository<Calibrator, Long> {


}