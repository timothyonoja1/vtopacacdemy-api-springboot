package com.vtopacademy.practicequestions;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PracticeQuestionRepository extends MongoRepository<PracticeQuestion, String> {

	List<PracticeQuestion> findByVideoID(String videoID); 

} 
