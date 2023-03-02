package peaksoft.projectXSpringBoot.service.serviceImpll;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.projectXSpringBoot.entity.Appointment;
import peaksoft.projectXSpringBoot.entity.Department;
import peaksoft.projectXSpringBoot.entity.Hospital;
import peaksoft.projectXSpringBoot.exceptions.DateTimeException;
import peaksoft.projectXSpringBoot.repository.*;
import peaksoft.projectXSpringBoot.service.AppointmentService;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.serviceImpll
 * 17.02.2023
 **/
@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    public List<Appointment> getAll(Long id) {
        try {
            return appointmentRepository.getAllByHospitalId(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        try {
            return appointmentRepository.findById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Appointment appointment, Long hospitalId) {
        try {
            Hospital hospital = hospitalRepository.findById(hospitalId).get();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointment.getDate(), format);

            if (date.isBefore(LocalDate.now())) {
                throw new DateTimeException();
            }

            appointment.setLocalDate(date);
            appointment.setPatient(patientRepository.findById(appointment.getPatientId()).get());
            appointment.setDoctor(doctorRepository.findById(appointment.getDoctorId()).get());

            List<Department> departments = doctorRepository.findById(appointment.getDoctorId()).get().getDepartments();
            boolean isTrue = false;
            for (Department department : departments) {
                if (department.getId().equals(appointment.getDepartmentId())) {
                    isTrue = true;
                    break;
                }
            }
            if (isTrue) {
                appointment.setDepartment(departmentRepository.findById(appointment.getDepartmentId()).get());
                hospital.addAppointment(appointment);
                appointmentRepository.save(appointment);
            } else {
                throw new RuntimeException();
            }

        } catch (DateTimeException d) {
            throw new DateTimeException();
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Long id, Appointment newAppointment) {
        try {
            Appointment appointment = appointmentRepository.findById(id).get();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(newAppointment.getDate(), format);

            if (date.isBefore(LocalDate.now())) {
                throw new DateTimeException();
            }


            List<Department> departments = doctorRepository.findById(newAppointment.getDoctorId()).get().getDepartments();
            boolean isTrue = false;
            for (Department department : departments) {
                if (department.getId().equals(newAppointment.getDepartmentId())) {
                    isTrue = true;
                    break;
                }
            }
            if (!isTrue) {
                throw new RuntimeException();
            }
            appointment.setLocalDate(date);
            appointment.setPatient(patientRepository.findById(newAppointment.getPatientId()).get());
            appointment.setDoctor(doctorRepository.findById(newAppointment.getDoctorId()).get());
            appointment.setDepartment(departmentRepository.findById(newAppointment.getDepartmentId()).get());

        } catch (DateTimeException e) {
            throw new DateTimeException();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Long id, Long hospitalId) {
        List<Hospital> hospitals = hospitalRepository.findAll();
        hospitals.forEach(h -> h.getAppointmentList().removeIf(a -> a.getId().equals(id)));
        appointmentRepository.deleteById(id);
    }
}
