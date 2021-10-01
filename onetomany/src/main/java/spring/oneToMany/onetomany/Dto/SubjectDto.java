package spring.oneToMany.onetomany.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class SubjectDto {

    private  Integer subject_Id;

    private  String subject_name;

    private String subject_author;

}
