package com.vtopacademy.practicequestions;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuizRepository extends MongoRepository<Quiz, String> {

	Quiz findByVideoID(Long videoID); 

} 
