package com.hospital_management.hospital.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserRoleDTO {

    private Integer id;

    private String userName;

    private String jwtToken;

    private String password;

    //private List<UserRole> roleList;

}
