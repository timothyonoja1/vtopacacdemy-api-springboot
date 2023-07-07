package com.vtopacademy.videos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtopacademy.subtopics.SubTopic;

public interface VideoRepository extends JpaRepository<Video, Long> {
	
	List<Video> findBySubTopic(SubTopic subTopic);       
	  
}

