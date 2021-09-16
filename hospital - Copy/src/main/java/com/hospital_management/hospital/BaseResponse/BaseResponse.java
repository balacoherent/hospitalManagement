package com.hospital_management.hospital.BaseResponse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class BaseResponse<T> {

     String statusCode;

     String StatusMsg;

    private T Data;

}
