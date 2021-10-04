package com.hospital_management.hospital.Repository;

import com.hospital_management.hospital.Entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepositroy extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByPatientId(Integer patientId);

    Page<Patient> searchAllByPatientNameLike(String s, Pageable paging);
}
