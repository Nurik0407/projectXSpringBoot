package peaksoft.projectXSpringBoot.service.serviceImpll;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.projectXSpringBoot.entity.Appointment;
import peaksoft.projectXSpringBoot.entity.Department;
import peaksoft.projectXSpringBoot.entity.Doctor;
import peaksoft.projectXSpringBoot.entity.Hospital;
import peaksoft.projectXSpringBoot.repository.AppointmentRepository;
import peaksoft.projectXSpringBoot.repository.DepartmentRepository;
import peaksoft.projectXSpringBoot.repository.DoctorRepository;
import peaksoft.projectXSpringBoot.repository.HospitalRepository;
import peaksoft.projectXSpringBoot.service.DepartmentService;


import java.util.ArrayList;
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
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentRepository departmentRepository;

    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    private final DoctorRepository doctorRepository;

    @Override
    public Department findById(Long id) {
        try {
            return departmentRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void save(Department department, Long id) {
        try {
            for (Department depart : departmentRepository.getAllByHospitalId(id)) {
                if (depart.getName().equals(department.getName())) {
                    throw new RuntimeException();
                }
            }
            department.setHospital(hospitalRepository.findById(id).get());
            departmentRepository.save(department);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Long id, Department newDepartment) {
        try {
            for (Department department : departmentRepository.getAllByHospitalId(departmentRepository.findById(id).get().getHospital().getId())) {
                if (department.getName().equals(newDepartment.getName()) && !department.getId().equals(id)) {
                    throw new RuntimeException();
                }
            }
            Department department = departmentRepository.findById(id).get();
            department.setName(newDepartment.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        Department department = departmentRepository.findById(id).get();
        List<Hospital> hospitals = hospitalRepository.findAll();


        for (Hospital hospital : hospitals) {
            List<Appointment> appointments = hospital.getAppointmentList();
            if (appointments != null) {
                List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDepartment().getId().equals(id)).toList();
                appointmentList.forEach(appointments::remove);
                appointmentList.forEach(s -> appointmentRepository.deleteById(s.getId()));
            }
        }

        List<Department> departments = department.getHospital().getDepartmentList();
        departments.removeIf(s -> s.getId().equals(id));

        List<Doctor> doctors = department.getDoctors();
        if (doctors != null) {
            doctors.forEach(d -> d.getDepartments().removeIf(s -> s.getId().equals(id)));
        }

        departmentRepository.deleteById(id);
    }

    @Override
    public List<Department> getAllByHospitalId(Long id) {
        try {
            return departmentRepository.getAllByHospitalId(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public List<Department> getDepartmentsByHospitalIdAndDoctorId(Long hospitalId, Long doctorId) {
        List<Department> departments = departmentRepository.getAllByHospitalId(hospitalId);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new NoSuchElementException("Not found!!!"));
        List<Department> list = doctor.getDepartments();

        if (!list.isEmpty()) {
            departments.removeAll(list);
        }
        return departments;

    }


}
