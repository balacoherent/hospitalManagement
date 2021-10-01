CREATE TABLE Student(
student_id int NOT NULL AUTO_INCREMENT,
student_Name varchar(255) NOT NULL,
student_Age varchar(255) NOT NULL,
fk_subject_Id int,
PRIMARY KEY (student_id)
);

alter table Subject
add foreign key (fk_subject_Id) references Student(student_id);