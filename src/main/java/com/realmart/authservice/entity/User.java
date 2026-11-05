package com.realmart.authservice.entity;

import com.realmart.authservice.controller.request.RegisterUserRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users",schema = "realmart")
public class User {
	public User(){
		role = "CUSTOMER";
		accountStatus = "ACTIVE";
	}
	
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Id
	@Column(name = "email")
	private String email;
	@Column(name = "mobile_no")
	private String mobileNo;
	@Column(name = "password")
	private String password;
	@Column(name = "role")
	private String role;
	@Column(name = "account_status")
	private String accountStatus;
	@Column(name = "user_id")
	private String userId;

	public static User mapRequestToEntity(RegisterUserRequest registerUserRequest) {
	User user = new User();
	user.setFirstName(registerUserRequest.getFirstName());
	user.setLastName(registerUserRequest.getLastName());
	user.setEmail(registerUserRequest.getEmail());
	user.setMobileNo(registerUserRequest.getMobileNo());
	return user;
	}
}
