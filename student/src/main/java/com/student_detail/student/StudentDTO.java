package com.student_detail.student;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class StudentDTO {
    private int id;
    private String name;
    private String email;
}
