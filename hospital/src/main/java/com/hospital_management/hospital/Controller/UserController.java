package com.hospital_management.hospital.Controller;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.BaseResponse.BaseResponseRep;
import com.hospital_management.hospital.DTO.UserDTO;
import com.hospital_management.hospital.DTO.UserRoleDTO;
import com.hospital_management.hospital.Entity.User;
import com.hospital_management.hospital.Service.UserService;
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

    @Autowired
    private UserService userService;

    @PostMapping(value="/save")
    public BaseResponseRep<User> Saveuser(@RequestBody UserDTO userDTO){
      BaseResponseRep<User> baseResponseRep=null;
      baseResponseRep = BaseResponseRep.<User>builder().Data(userInterface.Saveuser(userDTO)).build();
      return baseResponseRep;
    }

    @GetMapping(value = "/login")
    @ApiOperation(value = "user login ")
    public BaseResponseRep<UserRoleDTO> generateToken(@RequestBody UserRoleDTO userRoleDTO) {
        BaseResponseRep<UserRoleDTO> baseResponseRep=null;
        baseResponseRep = BaseResponseRep.<UserRoleDTO>builder().Data(userInterface.generateToken(userRoleDTO)).build();
        return baseResponseRep;
    }

    @RolesAllowed(value = "USER")
    @ApiOperation(value = "userList", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping(value ="/getAll")
    public BaseResponseRep <List<User>> listAll(){
        BaseResponseRep<List<User>> baseResponseRep= null;
        baseResponseRep = BaseResponseRep.<List<User>>builder().Data(userInterface.listAll()).build();
        return baseResponseRep;
    }

    @RolesAllowed(value="USER")
    @ApiOperation(value = "userList", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/userId/{id}")
    public BaseResponseRep<Optional<User>> FindByUserId(@PathVariable Integer id){
        BaseResponseRep<Optional<User>> baseResponseRep= null;
        baseResponseRep = BaseResponseRep.<Optional<User>>builder().Data(userInterface.FindByUserId(id)).build();
        return baseResponseRep;
    }

   /* @RolesAllowed(value="ADMIN")
    @ApiOperation(value = "userList", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/deleteSoft")
    public BaseResponseRep<Optional<User>> deletesoft(@RequestBody UserDTO userDTO){
        BaseResponseRep<Optional<User>> baseResponseRep= null;
        baseResponseRep = BaseResponseRep.<Optional<User>>builder().Data(userInterface.deletesoft(userDTO)).build();
        return baseResponseRep;
    }*/
   @RolesAllowed(value="ADMIN")
   @ApiOperation(value = "userList", authorizations = {@Authorization(value = "Bearer")})
   @DeleteMapping("/delete/{id}")
   public User deletesoft(@PathVariable int id){
       return userInterface.deleteById(id);
   }



    @RolesAllowed(value="USER")
    @ApiOperation(value = "userList", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/update")
    public BaseResponseRep<Optional<User>> updateInfo(@RequestBody UserDTO userDTO){
        BaseResponseRep<Optional<User>> baseResponseRep= null;
        baseResponseRep = BaseResponseRep.<Optional<User>>builder().Data(userInterface.updateUser(userDTO)).build();
        return baseResponseRep;
    }

    @RolesAllowed(value = "USER")
    @ApiOperation(value = "userList", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/pagination/{offset}/{pageSize}/{name}")
    private ApiResponse<User> getUserWithPagination(@PathVariable int offset, @PathVariable int pageSize, @PathVariable String name){
        return userInterface.GetUserWithPagination(offset, pageSize, name);
    }

}
