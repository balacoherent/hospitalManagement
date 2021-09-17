package com.hospital_management.hospital.DTO;


import com.hospital_management.hospital.Entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UserSignupRequestVO {
    private Integer id;

    private String userName;

    @Override
    public String toString() {
        return "UserSignupRequestVO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", jwtToken='" + jwtToken + '\'' +
                ", password='" + password + '\'' +
                ", roleList=" + roleList +
                '}';
    }

    public void setRoleList(List<UserRole> roleList) {
        this.roleList = roleList;
    }

    private String jwtToken;

    private String password;

    private List<UserRole> roleList;

}
