package com.hospital_management.hospital.Repository;

import com.hospital_management.hospital.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByUserId(Integer id);

}