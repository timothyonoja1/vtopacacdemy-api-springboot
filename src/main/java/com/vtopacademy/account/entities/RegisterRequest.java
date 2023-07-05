package com.vtopacademy.account.entities;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
	
	@NotBlank
	private final String email;
	
	@NotBlank 
	private final String username;
	
	@NotBlank
	private final String password; 
	
	public RegisterRequest(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

}