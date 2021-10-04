package com.hospital_management.hospital.Controller;

import com.hospital_management.hospital.BaseResponse.BaseResponseRep;
import com.hospital_management.hospital.DTO.DiseaseDTO;
import com.hospital_management.hospital.Entity.Disease;
import com.hospital_management.hospital.ServiceInterface.DiseaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RequestMapping("/disease")
@RestController
public class DiseaseController {
    @Autowired
    private DiseaseInterface diseaseInterface;

    @RolesAllowed(value="USER")
    @PostMapping("/addDisease")
    public BaseResponseRep<Disease> addDiseaseInfo(@RequestBody DiseaseDTO diseaseDTO) {
        BaseResponseRep<Disease> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Disease>builder().Data(diseaseInterface.AddDiseaseInfo(diseaseDTO)).build();
        return baseResponseRep;

    }

    @RolesAllowed(value="USER")
    @GetMapping("/getAll")
    public BaseResponseRep<List<Disease>> list(){
        BaseResponseRep<List<Disease>> baseResponseRep= null;
        baseResponseRep = BaseResponseRep.<List<Disease>>builder().Data(diseaseInterface.listAlldisease()).build();
        return baseResponseRep;
    }

  /*  @RolesAllowed(value="ADMIN")
    @PutMapping("/deleteSoft")
    public BaseResponseRep<Optional<Disease>>  deleteSoft(@RequestBody DiseaseDTO diseaseDTO){
        BaseResponseRep<Optional<Disease>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Disease>>builder().Data(diseaseInterface.DeleteSoftDisease(diseaseDTO)).build();
        return baseResponseRep;
    }*/

    @RolesAllowed(value="ADMIN")
    @DeleteMapping("/delete/{id}")
    public String deletesoft(@PathVariable int id) {
        diseaseInterface.deleteById(id);
        return "Success";
    }

    @RolesAllowed(value="USER")
    @PutMapping("/update")
    public BaseResponseRep<Optional<Disease>> updateDiseaseById(@RequestBody DiseaseDTO diseaseDTO){
        BaseResponseRep<Optional<Disease>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Disease>>builder().Data(diseaseInterface.UpdateDiseaseById(diseaseDTO)).build();
        return baseResponseRep;
    }

}
