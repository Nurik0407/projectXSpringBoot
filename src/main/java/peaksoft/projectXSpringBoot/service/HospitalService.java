package peaksoft.projectXSpringBoot.service;

import peaksoft.projectXSpringBoot.entity.Hospital;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service
 * 17.02.2023
 **/
public interface HospitalService {

    void saveHospital(Hospital hospital);

    Hospital findById(Long id);

    void update(Long id, Hospital newHospital);

    void delete(Long id);


    List<Hospital> getAllHospital(String keyWord);
}
