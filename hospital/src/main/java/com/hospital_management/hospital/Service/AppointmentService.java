package com.hospital_management.hospital.Service;

import com.hospital_management.hospital.DTO.AppointmentDTO;
import com.hospital_management.hospital.Entity.*;
import com.hospital_management.hospital.Repository.AppointmentRepository;
import com.hospital_management.hospital.Repository.DiseaseRepositroy;
import com.hospital_management.hospital.Repository.DoctorRepository;
import com.hospital_management.hospital.Repository.PatientRepositroy;
import com.hospital_management.hospital.ServiceInterface.AppointmentInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class AppointmentService implements AppointmentInterface {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DiseaseRepositroy diseaseRepositroy;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepositroy patientRepositroy;

    @Override
    public Appointment AddAppointmentInfo(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentName(appointmentDTO.getAppointmentName());
        appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        Appointment finalAppointment = appointment;
        appointmentDTO.getPatientId().forEach(patientDTO -> {
            Optional<Patient> patient=patientRepositroy.findByPatientId(patientDTO.getPatientId());
            if(patient.isPresent())
            {
                finalAppointment.setPatientId(patient.get());
            }
            else
            {
                throw new RuntimeException("data not found");
            }
        });
        Appointment finalAppointment1 = appointment;
        appointmentDTO.getDoctorId().forEach(doctorDTO -> {
            Optional<Doctor> doctor=doctorRepository.findByDoctorId(doctorDTO.getDoctorId());
            if (doctor.isPresent())
            {
                finalAppointment1.setDoctorId(doctor.get());
            }
            else
            {
                throw new RuntimeException("data not found");
            }
        });
        Appointment finalAppointment2 = appointment;
        appointmentDTO.getDiseaseId().forEach(diseaseDTO -> {
            Optional<Disease> disease=diseaseRepositroy.findByDiseaseId(diseaseDTO.getDiseaseId());
            if (disease.isPresent())
            {
                finalAppointment2.setDiseaseId(disease.get());
            }
            else
            {
                throw new RuntimeException("data not found");
            }
        });
        appointment= appointmentRepository.save(appointment);

        return appointment;
    }

    @Override
    public List<Appointment> ListAllAppointment() {
        List<Appointment> obj=appointmentRepository.findAll();
        return obj;
    }

    @Override
    public Optional<Appointment> GetAppointmentById(Integer id) {
        Optional<Appointment> appointment=appointmentRepository.findById(id);
        return appointment;
    }

   /* @Override
    public Optional<Appointment> DeleteSoftAppointment(AppointmentDTO appointmentDTO) {
        Optional<Appointment> existAppointment = appointmentRepository.findById(appointmentDTO.getAppointmentId());
        if (existAppointment.isPresent())
        {
            existAppointment.get().setIsDelete(1);
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        appointmentRepository.save(existAppointment.get());

        return existAppointment;
    }*/

    @Override
    public Appointment deleteById(int id){
        Appointment appointment = new Appointment();
        appointmentRepository.deleteById(id);
        return appointment;
    }

    @Override
    public Optional<Appointment> UpdateAppointmentById(AppointmentDTO appointmentDTO) {
        Optional<Appointment> existAppointment = appointmentRepository.findById(appointmentDTO.getAppointmentId());
        if (existAppointment.isPresent())
        {
            existAppointment.get().setAppointmentName(appointmentDTO.getAppointmentName());
            existAppointment.get().setAppointmentTime(appointmentDTO.getAppointmentTime());
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        appointmentDTO.getPatientId().forEach(patientDTO -> {
            Optional<Patient> patientId=patientRepositroy.findByPatientId(patientDTO.getPatientId());
            if (patientId.isPresent())
            {
                existAppointment.get().setPatientId(patientId.get());
            }
            else
            {
                throw new RuntimeException("data not found");
            }
        });

        appointmentDTO.getDoctorId().forEach(doctorDTO -> {
            Optional<Doctor> doctorId=doctorRepository.findByDoctorId(doctorDTO.getDoctorId());
            if (doctorId.isPresent())
            {
                existAppointment.get().setDoctorId(doctorId.get());
            }
           else
            {
                throw new RuntimeException("data not found");
            }
        });

        appointmentDTO.getDiseaseId().forEach(diseaseDTO -> {
            Optional<Disease> diseaseId=diseaseRepositroy.findByDiseaseId(diseaseDTO.getDiseaseId());
            if (diseaseId.isPresent())
            {
                existAppointment.get().setDiseaseId(diseaseId.get());
            }
            else
            {
                throw new RuntimeException("data not found");
            }
        });
      appointmentRepository.save(existAppointment.get());

        return existAppointment;
    }

}
