package com.hospital_management.hospital.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    private String token;
    private String userName;
    private String password;
}