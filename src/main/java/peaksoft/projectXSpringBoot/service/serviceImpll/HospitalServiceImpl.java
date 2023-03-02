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
            List<Hospital> hospitals = hospitalRepository.findAll();
            for (Hospital hospital1 : hospitals) {
                if (hospital1.getName().equals(hospital.getName())) {
                    throw new RuntimeException();
                }
            }
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

            List<Hospital> hospitals = hospitalRepository.findAll();
            for (Hospital hospital1 : hospitals) {
                if (hospital1.getName().equals(newHospital.getName()) && !hospital1.getId().equals(hospital.getId())) {
                    throw new RuntimeException();
                }
            }

            hospital.setName(newHospital.getName());
            hospital.setImage(newHospital.getImage());
            hospital.setAddress(newHospital.getAddress());
            hospitalRepository.save(hospital);
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
            if (keyWord != null) {
                return hospitalRepository.getAllHospital("%" + keyWord + "%");
            } else {
                return hospitalRepository.findAll();
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
        throw new RuntimeException();
    }
}
