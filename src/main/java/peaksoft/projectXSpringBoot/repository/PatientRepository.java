package peaksoft.projectXSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.projectXSpringBoot.entity.Patient;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository
 * 17.02.2023
 **/
public interface PatientRepository extends JpaRepository<Patient,Long> {

    @Query("select p from Patient  p join p.hospital h where h.id=:id")
    List<Patient> getAll(Long id);


}
