package com.vtopacademy.schools;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "schools", 
	uniqueConstraints = { 
		@UniqueConstraint(columnNames = "name")
	}
)
public class School {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		generator = "tutorial_generator")
	private Long schoolID; 
	
	private String name;
	
	private int number;
	
	public School() {
		
	}
	
	public School(String name, int number) {
		this.name = name;
		this.number = number;
	}

	public Long getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(Long schoolID) {
		this.schoolID = schoolID;
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof School))
			return false;
		
		School school = (School) o;
		
		return Objects.equals(this.schoolID, school.schoolID) 
				&& Objects.equals(this.name, school.name)
				&& Objects.equals(this.number, school.number);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.schoolID, this.name, this.number);
	}
	
	@Override
	public String toString() {
		return "School{" + "schoolID=" + this.schoolID + ", name='" + this.name 
				+ '\'' + ", number='" + this.number + '\'' + '}';
	}
	
}
