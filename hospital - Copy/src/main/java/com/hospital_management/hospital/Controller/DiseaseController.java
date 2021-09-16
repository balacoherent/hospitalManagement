package com.hospital_management.hospital.Controller;

import com.hospital_management.hospital.BaseResponse.BaseResponse;
import com.hospital_management.hospital.BaseResponse.BaseResponseRep;
import com.hospital_management.hospital.DTO.DiseaseDTO;
import com.hospital_management.hospital.Entity.Appointment;
import com.hospital_management.hospital.Entity.Disease;
import com.hospital_management.hospital.Service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RequestMapping("/disease")
@RestController
public class DiseaseController {
    @Autowired
    private DiseaseService diseaseService;

    @PostMapping("/addDisease")
    public BaseResponseRep<Disease> addDiseaseInfo(@RequestBody DiseaseDTO diseaseDTO) {
        BaseResponseRep<Disease> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Disease>builder().Data(diseaseService.AddDiseaseInfo(diseaseDTO)).build();
        return baseResponseRep;

    }
    @GetMapping("/getAll")
    public BaseResponseRep<List<Disease>> list(){
        BaseResponseRep<List<Disease>> baseResponseRep= null;
        baseResponseRep = BaseResponseRep.<List<Disease>>builder().Data(diseaseService.listAlldisease()).build();
        return baseResponseRep;
    }

    @PutMapping("/deleteSoft")
    public BaseResponseRep<Optional<Disease>>  deleteSoft(@RequestBody DiseaseDTO diseaseDTO){
        BaseResponseRep<Optional<Disease>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Disease>>builder().Data(diseaseService.DeleteSoftDisease(diseaseDTO)).build();
        return baseResponseRep;
    }

    @PutMapping("/update")
    public BaseResponseRep<Optional<Disease>> updateDiseaseById(@RequestBody DiseaseDTO diseaseDTO){
        BaseResponseRep<Optional<Disease>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Disease>>builder().Data(diseaseService.UpdateDiseaseById(diseaseDTO)).build();
        return baseResponseRep;
    }

}
