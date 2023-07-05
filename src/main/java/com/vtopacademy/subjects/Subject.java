package com.vtopacademy.subjects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vtopacademy.schools.School;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "subjects", 
	uniqueConstraints = { 
		@UniqueConstraint(columnNames = "name")
	}
)
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		generator = "comment_generator")
	private Long subjectID;  
	
	private String name; 
	
	private int number;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private School school;  
	
	public Subject() {
		
	}

	public Subject(String name, int number, School school) {
		super();
		this.name = name;
		this.number = number;
		this.school = school;
	}

	public Long getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(Long subjectID) {
		this.subjectID = subjectID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
	
	
}
