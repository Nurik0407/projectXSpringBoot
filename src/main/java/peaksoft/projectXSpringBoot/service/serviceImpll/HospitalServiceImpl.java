package peaksoft.projectXSpringBoot.service.serviceImpll;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.projectXSpringBoot.entity.Hospital;
import peaksoft.projectXSpringBoot.service.HospitalService;
import peaksoft.projectXSpringBoot.repository.HospitalRepository;

import java.util.List;

/**
 * Zholdoshov Nuradil
 * peaksoft.service.serviceImpll
 * 17.02.2023
 **/
@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;


    @Override
    public void saveHospital(Hospital hospital) {
        try {
            hospitalRepository.save(hospital);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Hospital findById(Long id) {
        try {
            return hospitalRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }

    @Override
    public void update(Long id, Hospital newHospital) {
        try {
            Hospital hospital = hospitalRepository.findById(id).get();
            hospital.setName(newHospital.getName());
            hospital.setImage(newHospital.getImage());
            hospital.setAddress(newHospital.getAddress());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        hospitalRepository.deleteById(id);

    }

    @Override
    public List<Hospital> getAllHospital(String keyWord) {
        try {
            String kewWord2 = "%" + keyWord + "%";
            if (keyWord != null) {
                return hospitalRepository.getAllHospital(kewWord2);
            } else {
                return hospitalRepository.findAll();
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
