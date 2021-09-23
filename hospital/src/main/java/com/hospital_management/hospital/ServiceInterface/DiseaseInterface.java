package com.hospital_management.hospital.ServiceInterface;

import com.hospital_management.hospital.DTO.DiseaseDTO;
import com.hospital_management.hospital.Entity.Disease;

import java.util.List;
import java.util.Optional;

public interface DiseaseInterface {
    Disease AddDiseaseInfo(DiseaseDTO diseaseDTO);

    List<Disease> listAlldisease();

    Optional<Disease> DeleteSoftDisease(DiseaseDTO diseaseDTO);

    Optional<Disease> UpdateDiseaseById(DiseaseDTO diseaseDTO);
}
