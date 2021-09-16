package com.hospital_management.hospital.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class PatientDTO {

    private Integer patientId;

    private String patientName;

    private List <UserDTO> userId;

    private int isActive;

    private int isDelete;

}
