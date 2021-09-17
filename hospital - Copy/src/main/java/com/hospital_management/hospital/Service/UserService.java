package com.hospital_management.hospital.Service;

import com.hospital_management.hospital.BaseResponse.ApiResponse;
import com.hospital_management.hospital.DTO.RoleDTO;
import com.hospital_management.hospital.DTO.UserDTO;
import com.hospital_management.hospital.DTO.UserSignupRequestVO;
import com.hospital_management.hospital.Entity.Role;
import com.hospital_management.hospital.Entity.User;
import com.hospital_management.hospital.Entity.UserRole;
import com.hospital_management.hospital.Repository.RoleRepository;
import com.hospital_management.hospital.Repository.UserRepository;
import com.hospital_management.hospital.Repository.UserRoleRepository;
import com.hospital_management.hospital.ServiceInterface.UserInterface;
import com.hospital_management.hospital.Util.Constants;
import com.hospital_management.hospital.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.transaction.Transactional;
import java.util.*;


@org.springframework.stereotype.Service
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
        user.setUserName(userDTO.getUserName());
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
                            .orElseThrow(() -> new RuntimeException("role is not here"));
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
    public UserSignupRequestVO logOfUser(UserSignupRequestVO userSignupRequestVO) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        List<Role> roles = new LinkedList<>();
        try {
            Optional<User> user = userRepository.findByUserName(userSignupRequestVO.getUserName());
            boolean status = bCrypt.matches(userSignupRequestVO.getPassword(), user.get().getPassword());
            if (user.isPresent() && status == true) {
                Optional<UserRole> userRoles = userRoleRepository.findById(user.get().getUserId());
                userRoles.stream().forEachOrdered(userRole -> {
                    Role role = userRole.getRole();
                    roles.add(role);
                });
                String Token = JwtUtil.generateToken(Constants.SIGNIN_KEY, user.get().getUserId(), "user", user.get().getUserName(), roles);
                userSignupRequestVO.setUserName(user.get().getUserName());
                userSignupRequestVO.setId(user.get().getUserId());
                userSignupRequestVO.setRoleList(user.get().getRoles());
                userSignupRequestVO.setJwtToken(Token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userSignupRequestVO;
    }
    public UserDetails loadByUserName(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepository.findByUserName(username);
        List<Role> roles = new LinkedList<>();
        if (userDetail == null) {
            throw new UsernameNotFoundException("user not found");
        } else {
            Optional<UserRole> userRoles = userRoleRepository.findById(userDetail.get().getUserId());
            userRoles.stream().forEachOrdered(userRole -> {
                Role role = userRole.getRole();
                roles.add(role);
            });
            return new org.springframework.security.core.userdetails.User(userDetail.get().getUserName(), userDetail.get().getPassword(), getAuthority(userDetail.get().getRoles()));
        }
    }

    private List getAuthority(List<UserRole> role){
        List authorities=new ArrayList();
        role.stream().forEachOrdered(role1 -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" +role1.getRole().getRoleName()));
        });
        return authorities;
    }


    @Override
    public Optional<User> getUserbyId(Integer id) {
        Optional<User> user=userRepository.findById(id);
        return user;
    }

    @Override
    public List<User> listall() {
        List<User> obj =userRepository.findAll();
        return obj;
    }

    @Override
    public Optional<User>  deletesoft(UserDTO userDTO) {
        Optional<User> existUser = userRepository.findByUserId(userDTO.getUserId());
        if (existUser.isPresent())
        {
            existUser.get().setIsDelete(1);
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        userRepository.save(existUser.get());
        return existUser;
    }

    @Override
    public Optional<User> updatebyId(UserDTO userDTO) {
        Optional<User> existUser = userRepository.findById(userDTO.getUserId());
        if (existUser.isPresent())
        {
            existUser.get().setUserId(userDTO.getUserId());
            existUser.get().setUserName(userDTO.getUserName());
            existUser.get().setPassword(userDTO.getPassword());
        }
        else
        {
            throw new RuntimeException("data not found");
        }
        userRepository.save(existUser.get());

        return existUser;
    }

    @Override
    public ApiResponse<User> GetUserWithPagination(int offset, int pageSize, String userName) {
        Pageable paging= PageRequest.of(offset,pageSize);
        Page<User> Users = userRepository.searchAllByUserNameLike("%" + userName + "%", paging);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setResponse(Users);
        apiResponse.setRecordCount(Users.getTotalPages());
        return apiResponse;
    }

}
