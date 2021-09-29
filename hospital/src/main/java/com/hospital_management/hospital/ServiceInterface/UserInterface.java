package com.hospital_management.hospital.ServiceInterface;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.DTO.UserDTO;
import com.hospital_management.hospital.DTO.UserRoleDTO;
import com.hospital_management.hospital.Entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserInterface {

    User Saveuser(UserDTO userDTO);

    UserRoleDTO generateToken(UserRoleDTO userRoleDTO);

    Optional<User> FindByUserId(Integer id);

    List<User> listAll();

    //Optional<User> deletesoft(UserDTO userDTO);

    Optional<User> updateUser(UserDTO userDTO);

    ApiResponse<User> GetUserWithPagination(int offset, int pageSize, String name);

    User deleteById(int id);
}