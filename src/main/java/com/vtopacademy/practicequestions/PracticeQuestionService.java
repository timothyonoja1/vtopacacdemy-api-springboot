package com.vtopacademy.practicequestions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component 
public class PracticeQuestionService {
	
	@Autowired
	QuizRepository quizRepository;
	
	public List<PracticeQuestion> getPracticeQuestions(Long videoID) {
		Quiz quiz = quizRepository.findByVideoID(videoID);
		return quiz.getPracticeQuestions();
	}
	
	public PracticeQuestion getPracticeQuestion(Long videoID, int number) {
		Quiz quiz = quizRepository.findByVideoID(videoID);
		for (PracticeQuestion practiceQuestion : quiz.getPracticeQuestions()) {
			if (practiceQuestion.getNumber() == number) {
				return practiceQuestion;
			}
		}
		return null;
	}
	
	public PracticeQuestion savePracticeQuestion(
		PracticeQuestion practiceQuestion) {
		Long videoID = practiceQuestion.getVideoID();
		Quiz quiz = quizRepository.findByVideoID(videoID);
		if (quiz == null) {
			List<PracticeQuestion> practiceQuestions = new ArrayList<>();
			practiceQuestions.add(practiceQuestion);
			quizRepository.save(new Quiz(videoID, practiceQuestions));
			return practiceQuestion;
		}
		quiz.getPracticeQuestions().add(practiceQuestion);
		quizRepository.save(quiz);
		return practiceQuestion;
	}
	
	public PracticeQuestion updatePracticeQuestion(
		PracticeQuestion practiceQuestion) {
		Long videoID = practiceQuestion.getVideoID();
		Quiz quiz = quizRepository.findByVideoID(videoID);
		for (PracticeQuestion dbEntry : quiz.getPracticeQuestions()) {
			if (dbEntry.getNumber() == practiceQuestion.getNumber()) {
				dbEntry.setExamID(practiceQuestion.getExamID());
				dbEntry.setNumber(practiceQuestion.getNumber());
				dbEntry.setQuestion(practiceQuestion.getQuestion());
				dbEntry.setOptionA(practiceQuestion.getOptionA());
				dbEntry.setOptionB(practiceQuestion.getOptionB());
				dbEntry.setOptionC(practiceQuestion.getOptionC());
				dbEntry.setOptionD(practiceQuestion.getOptionD()); 
				dbEntry.setCorrectOption(practiceQuestion.getCorrectOption());
				quizRepository.save(quiz);
				break;
			}
		}
		return practiceQuestion;
	}
	
	public boolean deletePracticeQuestion(Long videoID, int number) {
		List<PracticeQuestion> newPracticeQuestions = new ArrayList<>();
		Quiz quiz = quizRepository.findByVideoID(videoID);
		for (PracticeQuestion practiceQuestion : quiz.getPracticeQuestions()) {
			if (practiceQuestion.getNumber() != number) {
				newPracticeQuestions.add(practiceQuestion);
			}
		}
		quiz.setPracticeQuestions(newPracticeQuestions);
		quizRepository.save(quiz);
		return true;
	}
}
