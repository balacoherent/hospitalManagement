package spring.oneToMany.onetomany.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.oneToMany.onetomany.BaseResponse.BaseResponse;
import spring.oneToMany.onetomany.Dto.StudentDto;
import spring.oneToMany.onetomany.Entity.Student;
import spring.oneToMany.onetomany.Entity.Subject;
import spring.oneToMany.onetomany.Repository.StudentRepository;
import spring.oneToMany.onetomany.Repository.SubjectRepository;



import java.util.List;

@Service

public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;


    public BaseResponse addStu(StudentDto studentDto) {
        Student student = new Student();

        BaseResponse baseResponse = new BaseResponse();
        student.setStudentName(studentDto.getStudentName());
        student.setStudentAge(studentDto.getStudentAge());
        studentRepository.save(student);

        studentDto.getSubjectDto().stream().forEachOrdered(subjectDto -> {

            Subject subject = new Subject();
            subject.setSubject_name(subjectDto.getSubject_name());
            subject.setSubject_author(subjectDto.getSubject_author());
            subject.setStudent(student);
            subjectRepository.save(subject);
        });
        baseResponse.setData(studentDto);
        baseResponse.setStatusCode("200");
        baseResponse.setStatusMsg("success");
        return baseResponse;

    }
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    public Subject getStudentById(Integer id){
        return subjectRepository.findById(id).orElse(null);
    }

    public BaseResponse deleted() {
        BaseResponse baseResponse = new BaseResponse();
        studentRepository.deleteAll();
        baseResponse.setStatusCode("200");
        baseResponse.setStatusMsg("student info deleted successfully...");
        return baseResponse;
    }
    public BaseResponse updatedetail(StudentDto studentDto) {
        Student existstudent = studentRepository.findById(studentDto.getStudentId()).orElse(null);
        BaseResponse baseResponse=new BaseResponse();
        existstudent.setStudentName(studentDto.getStudentName());
        existstudent.setStudentAge(studentDto.getStudentAge());
        studentRepository.save(existstudent);
        studentDto.getSubjectDto().stream().forEachOrdered(subjectDto -> {
            Subject existsubject= subjectRepository.findById(subjectDto.getSubject_Id()).orElse(null);
            existsubject.setSubject_name(subjectDto.getSubject_name());
            existsubject.setSubject_author(subjectDto.getSubject_author());
            existsubject.setStudent(existstudent);
            subjectRepository.save(existsubject);
        });
        baseResponse.setData(studentDto);
        baseResponse.setStatusCode("200");
        baseResponse.setStatusMsg("success");
        return baseResponse;
    }
}