CREATE TABLE User(
user_id int NOT NULL AUTO_INCREMENT,
user_name varchar(255) NOT NULL,
password varchar(255) NOT NULL,
is_active int default 0,
is_delete int default 0,
created_at timestamp,
modified_at timestamp,
PRIMARY KEY (user_id)
);
CREATE TABLE Patient(
patient_id int NOT NULL AUTO_INCREMENT,
patient_name varchar(255) NOT NULL,
is_active int default 0,
is_delete int default 0,
created_at timestamp,
modified_at timestamp,
fk_user_id int,
PRIMARY KEY (patient_id)
);
CREATE TABLE Doctor(
doctor_id int NOT NULL AUTO_INCREMENT,
doctor_name varchar(255) NOT NULL,
is_active int default 0,
is_delete int default 0,
created_at timestamp,
modified_at timestamp,
fk_user_id int,
PRIMARY KEY (doctor_id)
);
CREATE TABLE Disease(
disease_id int NOT NULL AUTO_INCREMENT,
disease_name varchar(255) NOT NULL,
is_active int default 0,
is_delete int default 0,
created_at timestamp,
modified_at timestamp,
PRIMARY KEY (disease_id)
);
CREATE TABLE Appointment(
appointment_id int NOT NULL AUTO_INCREMENT,
appointment_name varchar(255) NOT NULL,
is_active int default 0,
is_delete int default 0,
created_at timestamp,
modified_at timestamp,
fk_patient_id int,
fk_doctor_id int,
fk_disease_id int,
PRIMARY KEY (appointment_id)
);

CREATE TABLE Role(
id int AUTO_INCREMENT,
role_name varchar(255),
PRIMARY KEY (id)
);

CREATE TABLE userrole(
id int AUTO_INCREMENT,
fk_user_id int NOT NULL,
fk_role_id int NOT NULL,
PRIMARY KEY (id)
);

alter table Patient
add foreign key (fk_user_id) references User(user_id);

alter table Doctor
add foreign key (fk_user_id) references User(user_id);

alter table Appointment
add foreign key (fk_patient_id) references Patient(patient_id),
add foreign key (fk_doctor_id) references Doctor(doctor_id),
add foreign key (fk_disease_id) references Disease(disease_id);

alter table userRole
add foreign key (fk_user_id) references User(user_id),
add foreign key (fk_role_id) references Role(id);