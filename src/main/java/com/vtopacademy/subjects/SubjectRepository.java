package com.vtopacademy.subjects;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtopacademy.schools.School;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	List<Subject> findBySchool(School school);      
	  
}

