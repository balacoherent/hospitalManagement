package com.hospital_management.hospital.Service;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.DTO.UserDTO;
import com.hospital_management.hospital.DTO.UserRoleDTO;
import com.hospital_management.hospital.Entity.Role;
import com.hospital_management.hospital.Entity.User;
import com.hospital_management.hospital.Exception.ControllerExceptions;
import com.hospital_management.hospital.Repository.RoleRepository;
import com.hospital_management.hospital.Repository.UserRepository;
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

    @Override
    public User Saveuser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        user.setPassword(bcrypt.encode(userDTO.getPassword()));
        user=userRepository.save(user);
        List<Role> roleList=new LinkedList<>();
        userDTO.getRoles().stream().forEachOrdered(role -> {
            Role role1=new Role();
            role1.setRoleName(role.getRoleName());
            roleList.add(role1);
        });
        user.setListofrole(roleList);
        return user;
    }

    /*private void saveRole(List<RoleDTO> roles, User userDetail) {
        try {
            List<UserRole> userRoles = new LinkedList<>();
            if (Objects.nonNull(roles) && roles.size() > 0) {
                roles.stream().forEachOrdered(role -> {
                    Role role1 = roleRepository.findById(role.getId())
                            .orElseThrow(() -> new ControllerExceptions("404", "role is not found"));
                    UserRole userRole = new UserRole();
                    userRole.setUser(userDetail);
                    userRole.setRole(role1);
                    userRoles.add(userRole);
                });
                userRoleRepository.saveAll(userRoles);
            }
        } catch (NoSuchElementException e) {
            throw new ControllerExceptions("901", "something went wrong");
        }
    }*/

    @Override
    public UserRoleDTO generateToken(UserRoleDTO userRoleDTO) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        List<Role> roles = new LinkedList<>();
        try {
            Optional<User> user = userRepository.findByName(userRoleDTO.getUserName());
            boolean status = bcrypt.matches(userRoleDTO.getPassword(), user.get().getPassword());
            if (user.isPresent() && status == true) {
                user.get().getListofrole().stream().forEachOrdered(role -> {
                    Role role1 = new Role();
                    role1.setRoleName(role.getRoleName());
                    roles.add(role);
                });
                String Token = JwtUtil.generateToken("secret", user.get().getId(), "user", user.get().getName(), roles);
                userRoleDTO.setUserName(user.get().getName());
                userRoleDTO.setId(user.get().getId());
                userRoleDTO.setJwtToken(Token);
            }
        }  catch (NoSuchElementException e) {
            throw new ControllerExceptions("401","Unauthorised");
        }
        return userRoleDTO;
    }

    public UserDetails loadByuserName(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepository.findByName(username);
        List<Role> roles = new LinkedList<>();
        if (userDetail == null) {
            throw new ControllerExceptions("404","User details Not Found");
        } else {
            userDetail.get().getListofrole().stream().forEachOrdered(role -> {
                Role role1 = new Role();
                role1.setRoleName(role.getRoleName());
                roles.add(role);
            });
            return new org.springframework.security.core.userdetails.User(userDetail.get().getName(), userDetail.get().getPassword(), getAuthority(roles));
        }
    }


    private List getAuthority(List<Role> role){
        List authorities=new ArrayList();
        role.stream().forEachOrdered(roleget -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" +roleget.getRoleName()));
        });
        return authorities;
    }

    @Override
    public Optional<User> FindByUserId(Integer id) {
        Optional<User> users = userRepository.findById(id);
        return users;
    }

    @Override
    public List<User> listAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    /*@Override
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
    }*/

    @Override
    public User deleteById(int id) {
        User user = new User();
        userRepository.deleteById(id);
        return user;
    }

    @Override
    public Optional<User> updateUser(UserDTO userDTO) {
        Optional<User> existUser = userRepository.findById(userDTO.getId());
        if (existUser.isPresent()) {
            existUser.get().setName(userDTO.getName());
            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
            existUser.get().setPassword(bcrypt.encode(userDTO.getPassword()));
            userRepository.save(existUser.get());
            List<Role> roleList=new LinkedList<>();
            userDTO.getRoles().stream().forEachOrdered(role -> {
                Role role1=new Role();
                role1.setRoleName(role.getRoleName());
                roleList.add(role1);
            });
            existUser.get().setListofrole(roleList);
        } else {
            throw new ControllerExceptions("901", "Something went wrong");
        }
        return existUser;
    }

    @Override
    public ApiResponse<User> GetUserWithPagination(int offset, int pageSize, String name) {
        ApiResponse apiResponse = new ApiResponse();
        try {
            Pageable paging = PageRequest.of(offset, pageSize);
            Page<User> Users = userRepository.searchAllByNameLike("%" + name + "%", paging);
            apiResponse.setResponse(Users);
            apiResponse.setRecordCount(Users.getTotalPages());

        } catch (NoSuchElementException e) {
            throw new ControllerExceptions("404", "No details found");
        }
        return apiResponse;
    }
}