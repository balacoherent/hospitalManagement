package com.hospital_management.hospital.Repository;

import com.hospital_management.hospital.Entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DiseaseRepositroy extends JpaRepository<Disease, Integer> {
    Optional<Disease> findByDiseaseId(Integer diseaseId);

}
