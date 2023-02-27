package peaksoft.projectXSpringBoot.service;


import peaksoft.projectXSpringBoot.entity.Appointment;

import java.util.List;
import java.util.Optional;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.02.2023
 **/
public interface AppointmentService {


    List<Appointment> getAll(Long id);

    Optional<Appointment> findById(Long id);

    void save(Appointment appointment,Long hospitalId);

    void update(Long id, Appointment newAppointment);

    void delete(Long id,Long hospitalId);
}
