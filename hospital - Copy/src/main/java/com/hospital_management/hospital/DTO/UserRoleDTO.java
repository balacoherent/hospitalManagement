package com.hospital_management.hospital.DTO;


import com.hospital_management.hospital.Entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class UserRoleDTO {

    private Integer id;

    private String userName;

    private String jwtToken;

    private String password;

    private List<UserRole> roleList;

}
