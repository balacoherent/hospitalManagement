package com.hospital_management.hospital.ServiceInterface;


import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.DTO.DoctorDTO;
import com.hospital_management.hospital.Entity.Doctor;

import java.util.Optional;

public interface DoctorInterface {
    Doctor AddDoctorInfo(DoctorDTO doctorDTO);

    Optional<Doctor> GetDoctorById(int id);

    Optional<Doctor> DeleteSoftDoctor(DoctorDTO doctorDTO);

    Optional<Doctor> UpdateDoctorById(DoctorDTO doctorDTO);

    ApiResponse<Doctor> GetDoctorWithPagination(int offset, int pageSize, String doctorName);
}