package com.hospital_management.hospital.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DiseaseDTO {

    private Integer diseaseId;

    private String diseaseName;

    private int isActive;

    private int isDelete;
}
