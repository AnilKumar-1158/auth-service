package com.realmart.authservice.service;

import com.realmart.authservice.entity.User;
import com.realmart.authservice.exception.DuplicateUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realmart.authservice.controller.request.RegisterUserRequest;
import com.realmart.authservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void register(RegisterUserRequest registerUserRequest) {
            Optional<User> userOptional = userRepository.findById(registerUserRequest.getEmail());
            if (userOptional.isPresent()) {
                throw new DuplicateUserException("Email is already registered");
            }
            Optional<User> userOpt = userRepository.findByMobileNo(registerUserRequest.getMobileNo());
            if (userOpt.isPresent()) {
                throw new DuplicateUserException("Mobile Number is already registered");
            }
            User user = User.mapRequestToEntity(registerUserRequest);
            String encryptedPassword = bCryptPasswordEncoder.encode(registerUserRequest.getPassword());
            user.setPassword(encryptedPassword);
            userRepository.save(user);
    }
}
