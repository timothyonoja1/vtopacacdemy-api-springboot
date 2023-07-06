package com.vtopacademy.topics;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vtopacademy.kclasses.Kclass;
import com.vtopacademy.subjects.Subject;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "topics", 
	uniqueConstraints = { 
		@UniqueConstraint(columnNames = "name")
	}
)
public class Topic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		generator = "comment_generator")
	private Long topicID;   
	
	private String name; 
	
	private int number;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Kclass kclass;  
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Subject subject;  
	
	public Topic() {
		
	}

	public Topic(String name, int number, 
			Kclass kclass, Subject subject) {
		this.name = name;
		this.number = number;
		this.kclass = kclass;
		this.subject = subject;
	}

	public Long getTopicID() {
		return topicID;
	}

	public void setTopicID(Long topicID) {
		this.topicID = topicID;
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

	public Kclass getKclass() {
		return kclass;
	}

	public void setKclass(Kclass kclass) {
		this.kclass = kclass;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	
}
