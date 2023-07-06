package com.vtopacademy.topics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtopacademy.kclasses.Kclass;
import com.vtopacademy.subjects.Subject;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
	
	Page<Topic> findByKclass(Kclass kclass, Pageable pageable);     
	Page<Topic> findBySubject(Subject subject, Pageable pageable);
	Page<Topic> findByKclassAndSubject(
			Kclass kclass, Subject subject, Pageable pageable);
	  
} 