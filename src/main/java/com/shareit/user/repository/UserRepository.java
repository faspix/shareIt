package com.shareit.user.repository;

import com.shareit.user.dto.UserDto;
import com.shareit.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    void deleteUserById(Long id);

}
