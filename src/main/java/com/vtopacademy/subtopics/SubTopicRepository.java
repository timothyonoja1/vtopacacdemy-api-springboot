package com.vtopacademy.subtopics;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtopacademy.topics.Topic;

public interface SubTopicRepository  extends JpaRepository<SubTopic, Long> {
	
	List<SubTopic> findByTopic(Topic topic);      
	  
}
