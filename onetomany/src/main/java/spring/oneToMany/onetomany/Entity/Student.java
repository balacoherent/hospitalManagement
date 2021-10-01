package spring.oneToMany.onetomany.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Transactional
@Data
@NoArgsConstructor
@Table(name="Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "student_Name")
    private String studentName;

    @Column(name = "student_Age")
    private Integer studentAge;

    @OneToMany(cascade =CascadeType.ALL )
    @JoinColumn(name = "fk_subject_Id")
    private List<Subject> subject;
}



