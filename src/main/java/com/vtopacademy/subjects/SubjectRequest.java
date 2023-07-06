package com.vtopacademy.subjects;

import jakarta.validation.constraints.NotBlank;

public class SubjectRequest {

	@NotBlank 
	private final String name;
	
	@NotBlank
	private final int number;
	
	@NotBlank 
	private final Long schoolID;

	public SubjectRequest(@NotBlank String name,
			@NotBlank int number, @NotBlank Long schoolID) {
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
