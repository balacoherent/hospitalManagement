package com.student_detail.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController
{
    @Autowired
    private StudentService studentService;

    @PostMapping(value = "/student_details")
    public BaseResponse info( @RequestBody StudentDTO studentDTO) {
        return studentService.detail(studentDTO);
    }

    @DeleteMapping("/delete/{id}")
    public Student delete(@PathVariable int id){
        return studentService.deleted(id);
    }
}
