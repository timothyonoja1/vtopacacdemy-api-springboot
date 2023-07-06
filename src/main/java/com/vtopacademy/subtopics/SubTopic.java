package com.vtopacademy.subtopics;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vtopacademy.topics.Topic;

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
public class SubTopic {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		generator = "comment_generator")
	private Long subTopicID;  
	
	private String name; 
	
	private int number;
	
	private boolean free;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Topic topic;  
	
	public SubTopic() {
		
	}

	public SubTopic(String name, int number, 
			boolean free, Topic topic) {
		super();
		this.name = name;
		this.number = number;
		this.free = free;
		this.topic = topic;
	}

	public Long getSubTopicID() {
		return subTopicID;
	}

	public void setSubTopicID(Long subTopicID) {
		this.subTopicID = subTopicID;
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

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
}
