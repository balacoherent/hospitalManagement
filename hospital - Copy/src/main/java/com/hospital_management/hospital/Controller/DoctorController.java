package com.hospital_management.hospital.Controller;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.BaseResponse.BaseResponseRep;
import com.hospital_management.hospital.DTO.DoctorDTO;
import com.hospital_management.hospital.Entity.Doctor;
import com.hospital_management.hospital.ServiceInterface.DoctorInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/doctor")
@RestController
public class DoctorController {
    @Autowired
    private DoctorInterface doctorInterface;

    @PostMapping("/addDoctor")
    public BaseResponseRep<Doctor> AddDoctorInfo(@RequestBody DoctorDTO doctorDTO) {
            BaseResponseRep<Doctor> baseResponseRep=null;
            baseResponseRep = BaseResponseRep.<Doctor>builder().Data(doctorInterface.AddDoctorInfo(doctorDTO)).build();
            return baseResponseRep;
        }

    @GetMapping("/doctorId/{id}")
    public BaseResponseRep<Optional<Doctor>> getDoctorById(@PathVariable int id){
        BaseResponseRep<Optional<Doctor>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Doctor>>builder().Data(doctorInterface.GetDoctorById(id)).build();
        return baseResponseRep;

    }

    @PutMapping("/deleteSoft")
    public BaseResponseRep<Optional<Doctor>>  deleteSoft(@RequestBody DoctorDTO doctorDTO){
        BaseResponseRep<Optional<Doctor>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Doctor>>builder().Data(doctorInterface.DeleteSoftDoctor(doctorDTO)).build();
        return baseResponseRep;
    }

    @PutMapping("/update")
    public BaseResponseRep<Optional<Doctor>> updateInfo(@RequestBody DoctorDTO doctorDTO){
        BaseResponseRep<Optional<Doctor>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Doctor>>builder().Data(doctorInterface.UpdateDoctorById(doctorDTO)).build();
        return baseResponseRep;
    }

    @GetMapping("/pagination/{offset}/{pageSize}/{doctorName}")
    private ApiResponse<Doctor> getDoctorWithPagination(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String doctorName){
        return doctorInterface.GetDoctorWithPagination(offset, pageSize, doctorName);
    }
}

