package com.laioffer.staybooking.repository;

import com.laioffer.staybooking.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  // SELECT * FROM users WHERE username = ?
  UserEntity findByUsername(String username);

  boolean existsByUsername(String username);
}
