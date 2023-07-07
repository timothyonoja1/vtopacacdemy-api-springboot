package com.vtopacademy.practicequestions;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Quizzes") 
public class Quiz {

	@Id
	private String id;
	
	private Long videoID;
	
	private List<PracticeQuestion> practiceQuestions;

	public Quiz(Long videoID, List<PracticeQuestion> practiceQuestions) {
		this.videoID = videoID;
		this.practiceQuestions = practiceQuestions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVideoID() {
		return videoID;
	}

	public void setVideoID(Long videoID) {
		this.videoID = videoID;
	}

	public List<PracticeQuestion> getPracticeQuestions() {
		return practiceQuestions;
	}

	public void setPracticeQuestions(List<PracticeQuestion> practiceQuestions) {
		this.practiceQuestions = practiceQuestions;
	}
	
	
}
