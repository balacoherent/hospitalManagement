package com.hospital_management.hospital.ServiceInterface;

import com.hospital_management.hospital.DTO.AppointmentDTO;
import com.hospital_management.hospital.Entity.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentInterface {
    Appointment AddAppointmentInfo(AppointmentDTO appointmentDTO);

    List<Appointment> ListAllAppointment();

    Optional<Appointment> GetAppointmentById(Integer id);

   // Optional<Appointment> DeleteSoftAppointment(AppointmentDTO appointmentDTO);

    Optional<Appointment> UpdateAppointmentById(AppointmentDTO appointmentDTO);

    Appointment deleteById(int id);
}