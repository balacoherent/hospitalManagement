package com.hospital_management.hospital.Controller;

import com.hospital_management.hospital.BaseResponse.BaseResponseRep;
import com.hospital_management.hospital.DTO.AppointmentDTO;
import com.hospital_management.hospital.Entity.Appointment;
import com.hospital_management.hospital.ServiceInterface.AppointmentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/appointment")
@RestController

public class AppointmentController {
    @Autowired
    private AppointmentInterface appointmentInterface;

    @PostMapping("/addAppointment")
    public BaseResponseRep<Appointment> addAppointmentInfo(@RequestBody AppointmentDTO appointmentDTO) {
            BaseResponseRep<Appointment> baseResponseRep=null;
            baseResponseRep = BaseResponseRep.<Appointment>builder().Data(appointmentInterface.AddAppointmentInfo(appointmentDTO)).build();
            return baseResponseRep;
    }

    @GetMapping("/getAll")
    public BaseResponseRep<List<Appointment>> list() {
        BaseResponseRep<List<Appointment>> baseResponseRep = null;
        baseResponseRep = BaseResponseRep.<List<Appointment>>builder().Data(appointmentInterface.ListAllAppointment()).build();
        return baseResponseRep;
    }

    @GetMapping("/appointmentId/{id}")
    public BaseResponseRep<Optional<Appointment>> getAppointmentById(@PathVariable Integer id){
        BaseResponseRep<Optional<Appointment>> baseResponseRep = null;
        baseResponseRep = BaseResponseRep.<Optional<Appointment>>builder().Data(appointmentInterface.GetAppointmentById(id)).build();
        return baseResponseRep;
    }

    @PutMapping("/deleteSoft")
    public BaseResponseRep<Optional<Appointment>>   deleteSoftAppointment(@RequestBody AppointmentDTO appointmentDTO){
        BaseResponseRep<Optional<Appointment>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Appointment>>builder().Data(appointmentInterface.DeleteSoftAppointment(appointmentDTO)).build();
        return baseResponseRep;
    }

    @PutMapping("/update")
    public BaseResponseRep<Optional<Appointment>> updateInfo(@RequestBody AppointmentDTO appointmentDTO){
        BaseResponseRep<Optional<Appointment>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Appointment>>builder().Data(appointmentInterface.UpdateAppointmentById(appointmentDTO)).build();
        return baseResponseRep;
    }


}