package com.hospital_management.hospital.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class DoctorDTO {

    private Integer doctorId;

    private String doctorName;

    private List<UserDTO> userId;

    private int isActive;

    private int isDelete;

}
