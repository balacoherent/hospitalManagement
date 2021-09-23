package com.hospital_management.hospital.Service;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.DTO.PatientDTO;
import com.hospital_management.hospital.Entity.Patient;
import com.hospital_management.hospital.Entity.User;
import com.hospital_management.hospital.Repository.PatientRepositroy;
import com.hospital_management.hospital.Repository.UserRepository;
import com.hospital_management.hospital.ServiceInterface.PatientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional

public class PatientService implements PatientInterface {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepositroy patientRepositroy;

    @Override
    public Patient AddPatientInfo(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setPatientName(patientDTO.getPatientName());
        Patient finalPatient = patient;
        patientDTO.getUserId().forEach(userDTO -> {
            Optional<User> users=userRepository.findById(userDTO.getId());
            if(users.isPresent())
            {
                finalPatient.setUsers(users.get());
            }
            else
            {
                throw new RuntimeException("data not found");
            }
        });
        patient.setPatientId(patientDTO.getPatientId());
        patient.setPatientName(patientDTO.getPatientName());
        patientRepositroy.save(patient);

        return patient;
    }

    @Override
    public Optional<Patient> GetPatientById(Integer id) {
        Optional<Patient> patient=patientRepositroy.findById(id);
        return patient;

    }

    @Override
    public Optional<Patient> DeleteSoftPatient(PatientDTO patientDTO) {
        Optional<Patient> existPatient = patientRepositroy.findByPatientId(patientDTO.getPatientId());
        if(existPatient.isPresent())
        {
            existPatient.get().setIsDelete(1);
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        patientRepositroy.save(existPatient.get());
        return existPatient;
    }

    @Override
    public Optional<Patient>  UpdatePatientById(PatientDTO patientDTO) {
        Optional<Patient> exitsPatient = patientRepositroy.findById(patientDTO.getPatientId());
        if (exitsPatient.isPresent())
        {
            exitsPatient.get().setPatientName(patientDTO.getPatientName());
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        patientDTO.getUserId().forEach(userDTO -> {
            Optional<User> users=userRepository.findById(userDTO.getId());
            if (users.isPresent())
            {
                exitsPatient.get().setUsers(users.get());
            }
            else
            {
                throw new RuntimeException("data not found");
            }

        });
        patientRepositroy.save(exitsPatient.get());
        return exitsPatient;
    }
    @Override
    public ApiResponse<Patient> GetPatientWithPagination(int offset, int pageSize, String patientName) {
        Pageable paging= PageRequest.of(offset,pageSize);
        Page<Patient> patients = patientRepositroy.searchAllByPatientNameLike("%" + patientName + "%", paging);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setResponse(patients);
        apiResponse.setRecordCount(patients.getTotalPages());
        return apiResponse;
    }
}
