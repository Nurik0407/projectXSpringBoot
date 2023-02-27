package peaksoft.projectXSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.projectXSpringBoot.entity.Appointment;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository
 * 17.02.2023
 **/
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Hospital h join h.appointmentList a where h.id=:hospitalId")
    List<Appointment> getAllByHospitalId(Long hospitalId);

}

