package com.hospital_management.hospital.BaseResponse;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder

public class BaseResponseRep<T> {

    @Builder.Default
    private String StatusCode= "200";
    @Builder.Default
    private String StatusMsg= "SUCCESS";
    private T Data;

}
