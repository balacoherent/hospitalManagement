package com.hospital_management.hospital.Repository;

import com.hospital_management.hospital.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

     List<User> findAll();


    Optional<User> findByName(String userName);

    Page<User> searchAllByNameLike(String s, Pageable paging);
}
