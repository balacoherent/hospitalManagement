package com.hospital_management.hospital.ServiceInterface;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.DTO.PatientDTO;
import com.hospital_management.hospital.Entity.Patient;

import java.util.Optional;

public interface PatientInterface {
    Patient AddPatientInfo(PatientDTO patientDTO);

    Optional<Patient> GetPatientById(Integer id);

   // Optional<Patient> DeleteSoftPatient(PatientDTO patientDTO);

    Optional<Patient> UpdatePatientById(PatientDTO patientDTO);

    ApiResponse<Patient> GetPatientWithPagination(int offset, int pageSize, String patientName);

    Patient deleteById(int id);
}
