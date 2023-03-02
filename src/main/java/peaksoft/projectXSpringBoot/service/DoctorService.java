package peaksoft.projectXSpringBoot.service;



import peaksoft.projectXSpringBoot.entity.Doctor;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.02.2023
 **/
public interface DoctorService {
    List<Doctor> getAllByHospitalId(Long id);

    Doctor findById(Long id);

    void save(Doctor doctor,Long id);

    void update(Long id,Doctor doctor);

    void delete(Long id);

    List<Doctor> getAllByDepartmentId(Long departmentId);

    void assign(Long doctorId, List<Long> departmentIdes);

}
