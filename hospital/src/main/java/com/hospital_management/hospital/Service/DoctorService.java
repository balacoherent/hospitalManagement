package com.hospital_management.hospital.Service;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.DTO.DoctorDTO;
import com.hospital_management.hospital.Entity.Doctor;
import com.hospital_management.hospital.Entity.User;
import com.hospital_management.hospital.Repository.DoctorRepository;
import com.hospital_management.hospital.Repository.UserRepository;
import com.hospital_management.hospital.ServiceInterface.DoctorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class DoctorService implements DoctorInterface {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Doctor AddDoctorInfo(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setDoctorName(doctorDTO.getDoctorName());
        doctorDTO.getUserId().forEach(userDTO -> {
            Optional<User> users=userRepository.findById(userDTO.getId());
            if (users.isPresent())
            {
                doctor.setUsers(users.get());
                doctor.setDoctorId(doctorDTO.getDoctorId());
                doctor.setDoctorName(doctorDTO.getDoctorName());
            }
            else
            {
                throw new RuntimeException("data not found ");
            }
        });
        doctorRepository.save(doctor);
        return doctor;
    }

    @Override
    public Optional<Doctor> GetDoctorById(int id) {
        Optional<Doctor> doctor=doctorRepository.findById(id);

        return doctor;
    }

    @Override
    public Optional<Doctor> DeleteSoftDoctor(DoctorDTO doctorDTO) {
        Optional<Doctor> existDoctor = doctorRepository.findByDoctorId(doctorDTO.getDoctorId());
        if (existDoctor.isPresent())
        {
            existDoctor.get().setIsDelete(1);
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        doctorRepository.save(existDoctor.get());
        return existDoctor;
    }

    @Override
    public Optional<Doctor>  UpdateDoctorById(DoctorDTO doctorDTO) {
        Optional<Doctor> exitsDoctor = doctorRepository.findById(doctorDTO.getDoctorId());
        if (exitsDoctor.isPresent())
        {
            exitsDoctor.get().setDoctorName(doctorDTO.getDoctorName());
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        doctorDTO.getUserId().forEach(userDTO -> {
            Optional<User> users=userRepository.findById(userDTO.getId());
            if (users.isPresent())
            {
                exitsDoctor.get().setUsers(users.get());
            }
            else
            {
                throw new RuntimeException("data not found");
            }

        });
        doctorRepository.save(exitsDoctor.get());
        return exitsDoctor;
    }

    @Override
    public ApiResponse<Doctor> GetDoctorWithPagination(int offset, int pageSize, String doctorName) {
        Pageable paging= PageRequest.of(offset,pageSize);
        Page<Doctor> doctors = doctorRepository.searchAllByDoctorNameLike("%" + doctorName + "%", paging);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setResponse(doctors);
        apiResponse.setRecordCount(doctors.getTotalPages());
        return apiResponse;
    }
}
