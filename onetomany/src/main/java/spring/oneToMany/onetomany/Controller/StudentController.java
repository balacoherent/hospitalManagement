package spring.oneToMany.onetomany.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.oneToMany.onetomany.BaseResponse.BaseResponse;
import spring.oneToMany.onetomany.Dto.StudentDto;
import spring.oneToMany.onetomany.Entity.Student;
import spring.oneToMany.onetomany.Entity.Subject;
import spring.oneToMany.onetomany.Service.StudentService;


import java.util.List;

@RequestMapping("/student_detail")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    public BaseResponse addStu(@RequestBody StudentDto studentDto) {
        return studentService.addStu(studentDto);
    }

    @GetMapping("/getAll")
    public List<Student> list() {
        return studentService.listAll();
    }

        @GetMapping("/studentbyid/{id}")
                public Subject findStudentById(@PathVariable int id){
            return studentService.getStudentById(id);
    }


    @DeleteMapping("/deleteAll")
    public BaseResponse deletedetail() {
        return studentService.deleted();
    }

    @PutMapping("/update")
    public BaseResponse updatedetail(@RequestBody StudentDto studentDto) {
        return studentService.updatedetail(studentDto);
    }

}
