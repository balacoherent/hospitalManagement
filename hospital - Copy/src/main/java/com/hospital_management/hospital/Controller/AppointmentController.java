package com.hospital_management.hospital.Controller;

import com.hospital_management.hospital.BaseResponse.BaseResponseRep;
import com.hospital_management.hospital.DTO.AppointmentDTO;
import com.hospital_management.hospital.Entity.Appointment;
import com.hospital_management.hospital.ServiceInterface.AppointmentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RequestMapping("/appointment")
@RestController

public class AppointmentController {
    @Autowired
    private AppointmentInterface appointmentInterface;

    @RolesAllowed(value="USER")
    @PostMapping("/addAppointment")
    public BaseResponseRep<Appointment> addAppointmentInfo(@RequestBody AppointmentDTO appointmentDTO) {
            BaseResponseRep<Appointment> baseResponseRep=null;
            baseResponseRep = BaseResponseRep.<Appointment>builder().Data(appointmentInterface.AddAppointmentInfo(appointmentDTO)).build();
            return baseResponseRep;
    }

    @RolesAllowed(value="USER")
    @GetMapping("/getAll")
    public BaseResponseRep<List<Appointment>> list() {
        BaseResponseRep<List<Appointment>> baseResponseRep = null;
        baseResponseRep = BaseResponseRep.<List<Appointment>>builder().Data(appointmentInterface.ListAllAppointment()).build();
        return baseResponseRep;
    }

    @RolesAllowed(value="USER")
    @GetMapping("/appointmentId/{id}")
    public BaseResponseRep<Optional<Appointment>> getAppointmentById(@PathVariable Integer id){
        BaseResponseRep<Optional<Appointment>> baseResponseRep = null;
        baseResponseRep = BaseResponseRep.<Optional<Appointment>>builder().Data(appointmentInterface.GetAppointmentById(id)).build();
        return baseResponseRep;
    }

  /*  @RolesAllowed(value="ADMIN")
    @PutMapping("/deleteSoft")
    public BaseResponseRep<Optional<Appointment>>   deleteSoftAppointment(@RequestBody AppointmentDTO appointmentDTO){
        BaseResponseRep<Optional<Appointment>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Appointment>>builder().Data(appointmentInterface.DeleteSoftAppointment(appointmentDTO)).build();
        return baseResponseRep;
    }*/
  @RolesAllowed(value="ADMIN")
  @DeleteMapping("/delete/{id}")
  public String deletesoft(@PathVariable int id) {
      BaseResponseRep<Appointment> baseResponseRep = null;
      baseResponseRep = BaseResponseRep.<Appointment>builder().Data(appointmentInterface.deleteById(id)).build();
      return "Success";
  }


    @RolesAllowed(value="USER")
    @PutMapping("/update")
    public BaseResponseRep<Optional<Appointment>> updateInfo(@RequestBody AppointmentDTO appointmentDTO){
        BaseResponseRep<Optional<Appointment>> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<Optional<Appointment>>builder().Data(appointmentInterface.UpdateAppointmentById(appointmentDTO)).build();
        return baseResponseRep;
    }


}