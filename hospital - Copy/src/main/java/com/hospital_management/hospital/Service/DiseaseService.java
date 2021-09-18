package com.hospital_management.hospital.Service;

import com.hospital_management.hospital.DTO.DiseaseDTO;
import com.hospital_management.hospital.Entity.Disease;
import com.hospital_management.hospital.Repository.DiseaseRepositroy;
import com.hospital_management.hospital.ServiceInterface.DiseaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class DiseaseService implements DiseaseInterface {

    @Autowired
    private DiseaseRepositroy diseaseRepositroy;

    @Override
    public Disease AddDiseaseInfo(DiseaseDTO diseaseDTO) {
        Disease disease = new Disease();
        disease.setDiseaseId(diseaseDTO.getDiseaseId());
        disease.setDiseaseName(diseaseDTO.getDiseaseName());
        diseaseRepositroy.save(disease);

        return disease;
    }

    @Override
    public List<Disease> listAlldisease() {
        List<Disease> obj=diseaseRepositroy.findAll();
        return obj;
    }

    @Override
    public Optional<Disease> DeleteSoftDisease(DiseaseDTO diseaseDTO) {
        Optional<Disease> existDisease = diseaseRepositroy.findByDiseaseId(diseaseDTO.getDiseaseId());
        if (existDisease.isPresent())
        {
            existDisease.get().setIsDelete(1);
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        diseaseRepositroy.save(existDisease.get());

        return existDisease;
    }

    @Override
    public Optional<Disease> UpdateDiseaseById(DiseaseDTO diseaseDTO) {
        Optional<Disease> existDisease = diseaseRepositroy.findById(diseaseDTO.getDiseaseId());
        if (existDisease.isPresent())
        {
            existDisease.get().setDiseaseName(diseaseDTO.getDiseaseName());
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        diseaseRepositroy.save(existDisease.get());
        return existDisease;
    }
}
