package com.vtopacademy.topics;

import jakarta.validation.constraints.NotBlank;

public class TopicRequest {

	@NotBlank
	private final String name;
	
	@NotBlank
	private final int number;
	
	@NotBlank 
	private final Long kclassID;
	
	@NotBlank 
	private final Long subjectID;

	public TopicRequest(String name, int number, 
			Long kclassID, Long subjectID) {
		this.name = name;
		this.number = number;
		this.kclassID = kclassID;
		this.subjectID = subjectID;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public Long getKclassID() {
		return kclassID;
	}

	public Long getSubjectID() {
		return subjectID;
	}
	
}
