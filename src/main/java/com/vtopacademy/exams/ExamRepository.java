package com.vtopacademy.exams;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vtopacademy.schools.School;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
	
	List<Exam> findBySchool(School school);
	
}
  