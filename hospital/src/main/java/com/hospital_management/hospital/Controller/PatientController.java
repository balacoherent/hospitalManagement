package com.hospital_management.hospital.Controller;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.BaseResponse.BaseResponseRep;
import com.hospital_management.hospital.DTO.PatientDTO;
import com.hospital_management.hospital.Entity.Patient;
import com.hospital_management.hospital.ServiceInterface.PatientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RequestMapping("/patient")
@RestController
public class PatientController {
    @Autowired
    private PatientInterface patientInterface;

    @RolesAllowed(value="USER")
    @PostMapping("/addPatient")
    public BaseResponseRep<Patient> addPatientInfo(@RequestBody PatientDTO patientDTO) {
        BaseResponseRep<Patient> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Patient>builder().Data(patientInterface.AddPatientInfo(patientDTO)).build();
        return baseResponseRep;

        }
    @RolesAllowed(value="USER")
    @GetMapping("/patientId/{id}")
    public BaseResponseRep<Optional<Patient>> getPatientById(@PathVariable Integer id){
        BaseResponseRep<Optional<Patient>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Patient>>builder().Data(patientInterface.GetPatientById(id)).build();
        return baseResponseRep;

    }
  /*  @RolesAllowed(value="ADMIN")
    @PutMapping("/deleteSoft")
    public BaseResponseRep<Optional<Patient>>  deleteSoft(@RequestBody PatientDTO patientDTO){
        BaseResponseRep<Optional<Patient>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Patient>>builder().Data(patientInterface.DeleteSoftPatient(patientDTO)).build();
        return baseResponseRep;
    }*/

    @RolesAllowed(value="ADMIN")
    @DeleteMapping("/delete/{id}")
    public BaseResponseRep<Patient> deletesoft(@PathVariable int id) {
        BaseResponseRep<Patient> baseResponseRep = null;
        baseResponseRep = BaseResponseRep.<Patient>builder().Data(patientInterface.deleteById(id)).build();
        return baseResponseRep;
    }

    @RolesAllowed(value="USER")
    @PutMapping("/update")
    public BaseResponseRep<Optional<Patient>> putupdateInfo(@RequestBody PatientDTO patientDTO){
        BaseResponseRep<Optional<Patient>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Patient>>builder().Data(patientInterface.UpdatePatientById(patientDTO)).build();
        return baseResponseRep;
    }

    @RolesAllowed(value = "USER")
    @GetMapping("/pagination/{offset}/{pageSize}/{patientName}")
    private ApiResponse<Patient> getPatientWithPagination(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String patientName){
        return patientInterface.GetPatientWithPagination(offset, pageSize, patientName);
    }
}


