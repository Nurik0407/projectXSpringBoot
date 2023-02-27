package peaksoft.projectXSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.projectXSpringBoot.entity.Doctor;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository
 * 17.02.2023
 **/
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("select h from Doctor h join h.hospital a where a.id=:id")
    List<Doctor> getAllByHospitalId(Long id);

    @Query("select l from Doctor l join l.departments d where d.id=:departmentId")
    List<Doctor> getAllByDepartmentId(Long departmentId);
}
