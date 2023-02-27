package peaksoft.projectXSpringBoot.service.serviceImpll;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.projectXSpringBoot.entity.Appointment;
import peaksoft.projectXSpringBoot.entity.Hospital;
import peaksoft.projectXSpringBoot.entity.Patient;
import peaksoft.projectXSpringBoot.repository.AppointmentRepository;
import peaksoft.projectXSpringBoot.repository.HospitalRepository;
import peaksoft.projectXSpringBoot.repository.PatientRepository;
import peaksoft.projectXSpringBoot.service.PatientService;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.serviceImpll
 * 17.02.2023
 **/
@Service
@RequiredArgsConstructor
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Patient> getAll(Long id) {
        return patientRepository.getAll(id);
    }

    @Override
    public Patient findById(Long id) {
        return patientRepository.findById(id).get();
    }

    @Override
    public void save(Patient patient, Long hospitalId) {
        try {
            patient.setHospital(hospitalRepository.findById(hospitalId).get());
            List<Patient> patients = patientRepository.findAll();
            if (patients != null) {
                for (Patient pat : patients) {
                    if (pat.getPhoneNumber().equals(patient.getPhoneNumber())) {
                        throw new RuntimeException();
                    }
                }
            }
            patientRepository.save(patient);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Long id, Patient newPatient) {
        try {
            Patient patient = patientRepository.findById(id).get();

            for (Patient pat : patientRepository.findAll()) {
                if (pat.getPhoneNumber().equals(newPatient.getPhoneNumber()) && !pat.getId().equals(id)) {
                    throw new RuntimeException();
                }
            }
            patient.setFirstName(newPatient.getFirstName());
            patient.setLastName(newPatient.getLastName());
            patient.setEmail(newPatient.getEmail());
            patient.setGender(newPatient.getGender());
            patient.setPhoneNumber(newPatient.getPhoneNumber());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Patient patient = patientRepository.findById(id).get();

            List<Hospital> hospitals = hospitalRepository.findAll();
            for (int z = 0; z < hospitals.size(); z++) {
                List<Appointment> appointments = hospitals.get(z).getAppointmentList();
                if (appointments != null) {
                    List<Appointment> appointmentList = appointments.stream().filter(s -> s.getPatient().getId().equals(id)).toList();
                    appointmentList.forEach(appointments::remove);
                    appointmentList.forEach(s -> appointmentRepository.deleteById(s.getId()));
                }
            }

            List<Patient> patients = patient.getHospital().getPatientList();
            patients.removeIf(s -> s.getId().equals(id));

            patientRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
