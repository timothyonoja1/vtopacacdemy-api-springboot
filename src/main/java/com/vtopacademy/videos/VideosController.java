package com.vtopacademy.videos;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vtopacademy.NotFoundException;
import com.vtopacademy.subtopics.SubTopic;
import com.vtopacademy.subtopics.SubTopicRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Video", description = "Video management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/videos")
public class VideosController {

	@Autowired
	private VideoRepository videoRepository;
	
	@Autowired
	private SubTopicRepository subTopicRepository;
	
	@Autowired
	private VideoModelAssembler assembler;
	
	@GetMapping("/by-subTopicID/{id}")  
	public CollectionModel<EntityModel<Video>> getVideosBySubTopicID(@PathVariable Long id) { 
		Long subTopicID = id; 
		
		SubTopic subTopic = subTopicRepository.findById(subTopicID) 
			.orElseThrow(() -> new NotFoundException("SubTopic", subTopicID.toString()));
		
		List<EntityModel<Video>> videos = videoRepository.findBySubTopic(subTopic)
			.stream().map(assembler::toModel).collect(Collectors.toList());
		
		return CollectionModel.of(videos);
	}
	
	// Single item
	@GetMapping("/{id}")
	public EntityModel<Video> getOneVideo(@PathVariable Long id) { 
		Long videoID = id; 
		Video video = videoRepository.findById(videoID) 
		   	.orElseThrow(() -> new NotFoundException("Video", videoID.toString()));

		return assembler.toModel(video);
	} 
	
	@PostMapping("")
	public ResponseEntity<?> createNewVideo(
			@Valid @RequestBody VideoRequest videoRequest) { 
		SubTopic subTopic = subTopicRepository.findById(videoRequest.getSubTopicID()) 
		    .orElseThrow(() -> new NotFoundException("SubTopic", videoRequest.getSubTopicID().toString()));
		Video newVideo = new Video(
			videoRequest.getMainYoutubeID(),
			videoRequest.getCorrectionYoutubeID(),
			videoRequest.isFree(),
			subTopic); 
			 
		EntityModel<Video> entityModel = assembler.toModel(videoRepository.save(newVideo));
			 
		return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.body(entityModel); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceVideo(
			@Valid @RequestBody VideoRequest videoRequest, @PathVariable Long id) {
		Long videoID = id;
		SubTopic subTopic = subTopicRepository.findById(videoRequest.getSubTopicID()) 
			    .orElseThrow(() -> new NotFoundException("SubTopic", videoRequest.getSubTopicID().toString()));
		Video newVideo = new Video(
			videoRequest.getMainYoutubeID(),
			videoRequest.getCorrectionYoutubeID(),
			videoRequest.isFree(),
			subTopic); 
		
	    Video updatedVideo = videoRepository.findById(videoID)
	  	    .map(video -> {
	  	    	video.setMainYoutubeID(newVideo.getMainYoutubeID());
	  	        video.setCorrectionYoutubeID(newVideo.getCorrectionYoutubeID());
	  	        video.setFree(newVideo.isFree());
	  	        video.setSubTopic(subTopic);
	  	        return videoRepository.save(video);
	  	    }) 
	  	    .orElseGet(() -> {
	  	    	newVideo.setVideoID(id);
	  	    	return videoRepository.save(newVideo);
	  	    });
	  	    
	    EntityModel<Video> entityModel = assembler.toModel(updatedVideo);
	  	     
	    return ResponseEntity .created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
	    	.toUri()) //
	    	.body(entityModel);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVideo(@PathVariable Long id) {
		Long videoID = id;
		videoRepository.deleteById(videoID); 
		return ResponseEntity.noContent().build(); 
	}
	
}
