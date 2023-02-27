package peaksoft.projectXSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.projectXSpringBoot.entity.Department;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository
 * 17.02.2023
 **/
public interface DepartmentRepository extends JpaRepository<Department,Long> {



@Query("select l from Department l join l.hospital h where h.id=:id")
    List<Department> getAllByHospitalId(Long id);
@Query("select l from Department  l join l.doctors d where d.id=:id")
    List<Department> getAllByDoc(Long id);


}

