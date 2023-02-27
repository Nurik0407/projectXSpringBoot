package peaksoft.projectXSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.projectXSpringBoot.entity.Hospital;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.repository
 * 17.02.2023
 **/
public interface HospitalRepository extends JpaRepository<Hospital,Long> {

    @Query("select h from Hospital h where h.name ilike (:keyWord)")
    List<Hospital> getAllHospital(String keyWord);
}
