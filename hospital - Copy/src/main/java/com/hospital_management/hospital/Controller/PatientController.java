package com.hospital_management.hospital.Controller;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.BaseResponse.BaseResponseRep;
import com.hospital_management.hospital.DTO.PatientDTO;
import com.hospital_management.hospital.Entity.Patient;
import com.hospital_management.hospital.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/patient")
@RestController
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/addPatient")
    public BaseResponseRep<Patient> addPatientInfo(@RequestBody PatientDTO patientDTO) {
        BaseResponseRep<Patient> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Patient>builder().Data(patientService.AddPatientInfo(patientDTO)).build();
        return baseResponseRep;

        }

    @GetMapping("/patientId/{id}")
    public BaseResponseRep<Optional<Patient>> getPatientById(@PathVariable Integer id){
        BaseResponseRep<Optional<Patient>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Patient>>builder().Data(patientService.GetPatientById(id)).build();
        return baseResponseRep;

    }

    @PutMapping("/deleteSoft")
    public BaseResponseRep<Optional<Patient>>  deleteSoft(@RequestBody PatientDTO patientDTO){
        BaseResponseRep<Optional<Patient>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Patient>>builder().Data(patientService.DeleteSoftPatient(patientDTO)).build();
        return baseResponseRep;

    }

    @PutMapping("/update")
    public BaseResponseRep<Optional<Patient>> putupdateInfo(@RequestBody PatientDTO patientDTO){
        BaseResponseRep<Optional<Patient>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Patient>>builder().Data(patientService.UpdatePatientById(patientDTO)).build();
        return baseResponseRep;
    }

    @GetMapping("/pagination/{offset}/{pageSize}/{patientName}")
    private ApiResponse<Patient> getPatientWithPagination(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String patientName){
        return patientService.GetPatientWithPagination(offset, pageSize, patientName);
    }
}


