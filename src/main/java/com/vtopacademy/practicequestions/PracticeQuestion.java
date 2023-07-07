package com.vtopacademy.practicequestions;

public class PracticeQuestion {
	
	private Long videoID;
	private Long examID;
	private int number;
	private String question;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private Option correctOption; 
	
	public PracticeQuestion(Long videoID, Long examID, int number, String question, 
			String optionA, String optionB, String optionC,
			String optionD, Option correctOption) {
		this.videoID = videoID;
		this.examID = examID;
		this.number = number;
		this.question = question;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.correctOption = correctOption;
	}
	
	// Getters and Setters

	public Long getVideoID() {
		return videoID;
	}

	public void setVideoID(Long videoID) {
		this.videoID = videoID;
	}

	public Long getExamID() { 
		return examID;
	}

	public void setExamID(Long examID) {
		this.examID = examID;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public Option getCorrectOption() {
		return correctOption;
	}

	public void setCorrectOption(Option correctOption) {
		this.correctOption = correctOption;
	}
	
}
