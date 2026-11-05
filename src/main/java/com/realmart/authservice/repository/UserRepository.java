package com.realmart.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.realmart.authservice.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findByMobileNo(String mobileNumber);
}
