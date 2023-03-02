package peaksoft.projectXSpringBoot.service.serviceImpll;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.projectXSpringBoot.entity.Appointment;
import peaksoft.projectXSpringBoot.entity.Doctor;
import peaksoft.projectXSpringBoot.service.DoctorService;
import peaksoft.projectXSpringBoot.repository.AppointmentRepository;
import peaksoft.projectXSpringBoot.repository.DepartmentRepository;
import peaksoft.projectXSpringBoot.repository.DoctorRepository;
import peaksoft.projectXSpringBoot.repository.HospitalRepository;


import java.util.List;
import java.util.NoSuchElementException;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.serviceImpll
 * 17.02.2023
 **/
@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {


    private final DoctorRepository doctorRepository;

    private final HospitalRepository hospitalRepository;

    private final DepartmentRepository departmentRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Doctor> getAllByHospitalId(Long id) {
        try {
            return doctorRepository.getAllByHospitalId(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public Doctor findById(Long id) {
        try {
            return doctorRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }


    @Override
    public void save(Doctor doctor, Long id) {
        try {
            doctor.setHospital(hospitalRepository.findById(id).get());
            doctorRepository.save(doctor);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Long id, Doctor doctor) {
        try {
            Doctor oldDoctor = doctorRepository.findById(id).get();
            oldDoctor.setFirstName(doctor.getFirstName());
            oldDoctor.setLastName(doctor.getLastName());
            oldDoctor.setImage(doctor.getImage());
            oldDoctor.setEmail(doctor.getEmail());
            oldDoctor.setPosition(doctor.getPosition());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Doctor doctor = doctorRepository.findById(id).get();
        List<Appointment> appointments = doctor.getHospital().getAppointmentList();

        if (appointments != null) {
            List<Appointment> appointmentList = appointments.stream().filter(a -> a.getDoctor().getId().equals(id)).toList();
            appointmentList.forEach(appointments::remove);
            appointmentList.forEach(s -> appointmentRepository.deleteById(s.getId()));
        }

        List<Doctor> doctors = doctor.getHospital().getDoctorList();
        doctors.removeIf(s -> s.getId().equals(id));

        doctorRepository.deleteById(id);
    }

    @Override
    public List<Doctor> getAllByDepartmentId(Long departmentId) {
        return doctorRepository.getAllByDepartmentId(departmentId);
    }

    @Override
    public void assign(Long doctorId, List<Long> departmentIdes) {
        Doctor doctor = doctorRepository.findById(doctorId).
                orElseThrow(() -> new NoSuchElementException("Not found!!!"));
        departmentIdes.
                forEach(s -> doctor.addDepartment(departmentRepository.findById(s).
                        orElseThrow(() -> new NoSuchElementException("Not found!"))));
    }
}
