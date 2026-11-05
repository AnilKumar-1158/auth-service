package com.realmart.authservice.controller;

import com.realmart.authservice.controller.request.UserLoginRequest;
import com.realmart.authservice.controller.response.ApiResponse;
import com.realmart.authservice.controller.response.LoginResponse;
import com.realmart.authservice.dto.CustomUserDetails;
import com.realmart.authservice.model.TokenDetails;
import com.realmart.authservice.service.JwtService;
import com.realmart.authservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.realmart.authservice.controller.request.RegisterUserRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@PostMapping(path = "/register")
	public ResponseEntity<ApiResponse> register(@RequestBody RegisterUserRequest registerUser){
		userService.register(registerUser);
		return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED.value(),
				"User Registered successfully"), HttpStatus.CREATED);
	}

	@PostMapping(path = "/login")
	public ResponseEntity<LoginResponse> login(@RequestBody UserLoginRequest userLoginRequest){
	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				userLoginRequest.getEmail(), userLoginRequest.getPassword()));
	CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getUserId();
		String accessToken = jwtService.generateToken(authentication.getName(), authentication.getAuthorities(), userId);
	LoginResponse responseDetails = LoginResponse.builder()
				.status(HttpStatus.OK.value())
				.message("User Login Successful")
				.tokenDetails(
						TokenDetails.builder()
						.accessToken(accessToken)
						.expiresIn(900)
						.tokenType("Bearer")
						.build())
			    .build();
	return ResponseEntity.ok(responseDetails);
	}
}
