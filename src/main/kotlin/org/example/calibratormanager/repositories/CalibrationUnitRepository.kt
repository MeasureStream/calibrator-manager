package org.example.calibratormanager.repositories;

import org.example.calibratormanager.entities.CalibrationUnit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository;

@Repository
 interface CalibrationUnitRepository: JpaRepository<CalibrationUnit, Long> {
}
