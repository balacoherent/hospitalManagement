package com.student_detail.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public BaseResponse detail(StudentDTO studentDTO)
    {
        Student student=new Student();
        BaseResponse baseResponse=new BaseResponse();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        studentRepository.save(student);
        baseResponse.setStatusCode("200");
        baseResponse.setStatusMsg("sucess");
        return  baseResponse;
    }

    public Student deleted(int id) {
        Student student = new Student();
        studentRepository.deleteById(id);
        return student;
    }
}
