package peaksoft.projectXSpringBoot.service;



import peaksoft.projectXSpringBoot.entity.Department;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.02.2023
 **/
public interface DepartmentService {


    Department findById(Long id);

    void save(Department department,Long id);

    void update(Long id,Department newDepartment);

    void delete(Long id);
    List<Department> getAllByHospitalId(Long id);



}
