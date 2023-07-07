package com.vtopacademy.videos;

import jakarta.validation.constraints.NotBlank;

public class VideoRequest {
	
	@NotBlank
	private final String mainYoutubeID; 
	
	@NotBlank
	private final String correctionYoutubeID;
	
	@NotBlank
	private final boolean free;
	
	@NotBlank
	private final Long subTopicID;

	public VideoRequest(String mainYoutubeID, 
			String correctionYoutubeID, 
			boolean free, Long subTopicID) {
		super();
		this.mainYoutubeID = mainYoutubeID;
		this.correctionYoutubeID = correctionYoutubeID;
		this.free = free;
		this.subTopicID = subTopicID;
	}

	public String getMainYoutubeID() {
		return mainYoutubeID;
	}

	public String getCorrectionYoutubeID() {
		return correctionYoutubeID;
	}

	public boolean isFree() {
		return free;
	}

	public Long getSubTopicID() {
		return subTopicID;
	}

	
}
