package com.hospital_management.hospital.Service;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.DTO.RoleDTO;
import com.hospital_management.hospital.DTO.UserDTO;
import com.hospital_management.hospital.DTO.UserRoleDTO;
import com.hospital_management.hospital.Entity.Role;
import com.hospital_management.hospital.Entity.User;
import com.hospital_management.hospital.Entity.UserRole;
import com.hospital_management.hospital.Repository.RoleRepository;
import com.hospital_management.hospital.Repository.UserRepository;
import com.hospital_management.hospital.Repository.UserRoleRepository;
import com.hospital_management.hospital.ServiceInterface.UserInterface;
import com.hospital_management.hospital.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional

public class UserService implements UserInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public User Saveuser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user.setPassword(bcrypt.encode(userDTO.getPassword()));
        userRepository.save(user);
        saveRole(userDTO.getRoles(), user);
        return user;
    }
    private void saveRole(List<RoleDTO> roles, User userDetail) {
        try {
            List<UserRole> userRoles = new LinkedList<>();
            if (Objects.nonNull(roles) && roles.size() > 0) {
                roles.stream().forEachOrdered(role -> {
                    Role role1 = roleRepository.findById(role.getId())
                            .orElseThrow(() -> new RuntimeException("role not found"));
                    UserRole userRole = new UserRole();
                    userRole.setUser(userDetail);
                    userRole.setRole(role1);
                    userRoles.add(userRole);
                });
                userRoleRepository.saveAll(userRoles);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserRoleDTO logOfUser(UserRoleDTO userRoleDTO) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        List<Role> roles = new LinkedList<>();
        try {
            Optional<User> user = userRepository.findByName(userRoleDTO.getUserName());
            boolean status = bcrypt.matches(userRoleDTO.getPassword(), user.get().getPassword());
            if (user.isPresent() && status == true) {
                List<UserRole> userRoles = userRoleRepository.findByUserId(user.get().getId());
                userRoles.stream().forEachOrdered(userRole -> {
                    Role role = userRole.getRole();
                    roles.add(role);
                });
                String Token = JwtUtil.generateToken("secret", user.get().getId(), "user", user.get().getName(), roles);
                userRoleDTO.setUserName(user.get().getName());
                userRoleDTO.setId(user.get().getId());
                userRoleDTO.setRoleList(user.get().getRoles());
                userRoleDTO.setJwtToken(Token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userRoleDTO;
    }

    public UserDetails loadByuserName(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepository.findByName(username);
        List<Role> roles = new LinkedList<>();
        if (userDetail == null) {
            throw new RuntimeException("USER NOT FOUND");
        }
        else{
            List<UserRole> userRoles = userRoleRepository.findByUserId(userDetail.get().getId());
            userRoles.stream().forEachOrdered(userRole -> {
                Role role = userRole.getRole();
                roles.add(role);
            });
            return new org.springframework.security.core.userdetails.User(userDetail.get().getName(), userDetail.get().getPassword(), getAuthority(roles));
        }
    }

    private List getAuthority(List<Role> role){
        List authorities=new ArrayList();
        role.stream().forEachOrdered(role1 -> {
            authorities.add(new SimpleGrantedAuthority("ROLE" +role1.getRoleName()));
        });
        return authorities;
    }

    @Override
    public Optional<User> FindByUserId(Integer id) {
        Optional<User> users=userRepository.findById(id);
        return users;
    }

    @Override
    public List<User> listAll() {
        List<User> users =userRepository.findAll();
        return users;
    }

    @Override
    public Optional<User>  deletesoft(UserDTO userDTO) {
        Optional<User> existUser = userRepository.findById(userDTO.getId());
        if (existUser.isPresent())
        {
            existUser.get().setIsDelete(1);
            userRepository.save(existUser.get());
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        return existUser;
    }

    @Override
    public Optional<User> updateUser(UserDTO userDTO) {
        Optional<User> existUser = userRepository.findById(userDTO.getId());
        if (existUser.isPresent())
        {
            existUser.get().setName(userDTO.getName());
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            existUser.get().setPassword(bcrypt.encode(userDTO.getPassword()));
            userRepository.save(existUser.get());
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        return existUser;
    }

    @Override
    public ApiResponse<User> GetUserWithPagination(int offset, int pageSize, String name) {
        Pageable paging= PageRequest.of(offset,pageSize);
        Page<User> Users = userRepository.searchAllByNameLike("%" + name + "%", paging);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setResponse(Users);
        apiResponse.setRecordCount(Users.getTotalPages());
        return apiResponse;
    }

}
