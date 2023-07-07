package com.vtopacademy.videos;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vtopacademy.subtopics.SubTopic;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Video {
 
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		generator = "comment_generator")
	private Long videoID;   
	
	private String mainYoutubeID; 
	
	private String correctionYoutubeID;
	
	private boolean free;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private SubTopic subTopic;

	public Video(String mainYoutubeID, String correctionYoutubeID, 
			boolean free, SubTopic subTopic) {
		this.mainYoutubeID = mainYoutubeID;
		this.correctionYoutubeID = correctionYoutubeID;
		this.free = free;
		this.subTopic = subTopic;
	}

	public Long getVideoID() {
		return videoID;
	}

	public void setVideoID(Long videoID) {
		this.videoID = videoID;
	}

	public String getMainYoutubeID() {
		return mainYoutubeID;
	}

	public void setMainYoutubeID(String mainYoutubeID) {
		this.mainYoutubeID = mainYoutubeID;
	}

	public String getCorrectionYoutubeID() {
		return correctionYoutubeID;
	}

	public void setCorrectionYoutubeID(String correctionYoutubeID) {
		this.correctionYoutubeID = correctionYoutubeID;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public SubTopic getSubTopic() {
		return subTopic;
	}

	public void setSubTopic(SubTopic subTopic) {
		this.subTopic = subTopic;
	}  
	
	
}
