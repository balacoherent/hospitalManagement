package com.hospital_management.hospital.DTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter

public class AppointmentDTO {

    private Integer appointmentId;

    private String appointmentName;

    private Timestamp appointmentTime;

    private List<DiseaseDTO> diseaseId;

    private List<PatientDTO> patientId;

    private List<DoctorDTO> doctorId;

    private int isActive;

    private int isDelete;

}
