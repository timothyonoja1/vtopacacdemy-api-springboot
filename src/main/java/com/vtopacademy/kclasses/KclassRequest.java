package com.vtopacademy.kclasses;

import jakarta.validation.constraints.NotBlank;

public class KclassRequest {
	
	@NotBlank
	private final String name;
	
	@NotBlank
	private final int number;
	
	@NotBlank
	private final Long schoolID;
	
	public KclassRequest(String name, int number, Long schoolID) {
		this.name = name;
		this.number = number;
		this.schoolID = schoolID;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number; 
	}

	public Long getSchoolID() {
		return schoolID;
	}
	 
	
}