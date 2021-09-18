package com.hospital_management.hospital.Repository;

import com.hospital_management.hospital.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserId(Integer userId);

    Page<User> searchAllByUserNameLike(String s, Pageable paging);

    Optional<User> findByUserName(String userName);
}
