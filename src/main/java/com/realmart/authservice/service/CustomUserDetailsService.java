package com.realmart.authservice.service;

import com.realmart.authservice.dto.CustomUserDetails;
import com.realmart.authservice.entity.User;
import com.realmart.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<User> userOptional = userRepository.findById(username);
      User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
      return new CustomUserDetails(user);
    }
}
