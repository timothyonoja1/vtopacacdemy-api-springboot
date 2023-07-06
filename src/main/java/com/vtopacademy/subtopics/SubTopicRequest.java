package com.vtopacademy.subtopics;

import jakarta.validation.constraints.NotBlank;

public class SubTopicRequest {
	
	@NotBlank 
	private final String name;
	
	@NotBlank
	private final int number;
	
	@NotBlank
	private final boolean free;
	
	@NotBlank 
	private final Long topicID; 

	public SubTopicRequest(String name, int number, 
			boolean free, Long topicID) {
		super();
		this.name = name;
		this.number = number;
		this.free = free;
		this.topicID = topicID;
	}

	public String getName() {
		return name;
	}

	public int getNumber() {
		return number;
	}

	public boolean isFree() {
		return free;
	}

	public Long getTopicID() {
		return topicID;
	}
	
}
