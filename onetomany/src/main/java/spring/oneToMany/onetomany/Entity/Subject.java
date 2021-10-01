package spring.oneToMany.onetomany.Entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Transactional
@Data
@NoArgsConstructor
@Table(name = "Subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_Id")
    private Integer subject_Id;

    @Column(name = "subject_name")
    private String subject_name;

    @Column(name = "subject_author")
    private String subject_author;

    @ManyToOne(cascade =CascadeType.ALL)
    @JoinColumn(name = "fk_subject_Id")
    private Student student;
}
