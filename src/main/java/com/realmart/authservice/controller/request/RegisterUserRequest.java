package com.realmart.authservice.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
	
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNo;
	private String password;

}
