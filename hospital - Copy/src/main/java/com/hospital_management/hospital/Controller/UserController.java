package com.hospital_management.hospital.Controller;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.BaseResponse.BaseResponseRep;
import com.hospital_management.hospital.DTO.UserDTO;
import com.hospital_management.hospital.DTO.UserSignupRequestVO;
import com.hospital_management.hospital.Entity.User;
import com.hospital_management.hospital.ServiceInterface.UserInterface;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;


@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserInterface userInterface;

    @PostMapping(value="/save")
    public BaseResponseRep<User> Saveuser(@RequestBody UserDTO userDTO){
      BaseResponseRep<User> baseResponseRep=null;
      baseResponseRep = BaseResponseRep.<User>builder().Data(userInterface.Saveuser(userDTO)).build();
      return baseResponseRep;
    }

    @GetMapping(value = "/login")
    @ApiOperation(value = "user login ")
    public BaseResponseRep<UserSignupRequestVO> logOfUser(@RequestBody UserSignupRequestVO userSignupRequestVO) {
        BaseResponseRep<UserSignupRequestVO> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<UserSignupRequestVO>builder().Data(userInterface.logOfUser(userSignupRequestVO)).build();
        return baseResponseRep;
    }

    @RolesAllowed(value = "ADMIN")
    @GetMapping(value ="/getAll")
    @Authorization(value = "Bearer")
    public BaseResponseRep <List<User>> listAll(){
        BaseResponseRep<List<User>> baseResponseRep= null;
        baseResponseRep = BaseResponseRep.<List<User>>builder().Data(userInterface.listall()).build();
        return baseResponseRep;
    }

    @GetMapping("/userid/{id}")
    @Authorization(value = "Bearer")
    public BaseResponseRep<Optional<User>> getUserById(@PathVariable Integer id){
        BaseResponseRep<Optional<User>> baseResponseRep= null;
        baseResponseRep = BaseResponseRep.<Optional<User>>builder().Data(userInterface.getUserbyId(id)).build();
        return baseResponseRep;
    }

    @PutMapping("/deleteSoft")
    @Authorization(value = "Bearer")
    public BaseResponseRep<Optional<User>> deletesoft(@RequestBody UserDTO userDTO){
        BaseResponseRep<Optional<User>> baseResponseRep= null;
        baseResponseRep = BaseResponseRep.<Optional<User>>builder().Data(userInterface.deletesoft(userDTO)).build();
        return baseResponseRep;
    }

    @PutMapping("/update")
    @Authorization(value = "Bearer")
    public BaseResponseRep<Optional<User>> updateInfo(@RequestBody UserDTO userDTO){
        BaseResponseRep<Optional<User>> baseResponseRep= null;
        baseResponseRep = BaseResponseRep.<Optional<User>>builder().Data(userInterface.updatebyId(userDTO)).build();
        return baseResponseRep;
    }

    @GetMapping("/pagination/{offset}/{pageSize}/{userName}")
    @Authorization(value = "Bearer")
    private ApiResponse<User> getUserWithPagination(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String userName){
        return userInterface.GetUserWithPagination(offset, pageSize, userName);
    }

}
