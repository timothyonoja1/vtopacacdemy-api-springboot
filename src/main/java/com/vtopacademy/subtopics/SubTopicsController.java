package com.vtopacademy.subtopics;

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
import com.vtopacademy.topics.Topic;
import com.vtopacademy.topics.TopicRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "SubTopic", description = "SubTopic management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subtopics")
public class SubTopicsController {
	
	@Autowired 
	private SubTopicRepository subTopicRepository; 
	
	@Autowired 
	private TopicRepository topicRepository; 
	
	@Autowired 
	private SubTopicModelAssembler assembler;
	
	@GetMapping("/by-topicID/{id}")  
	public CollectionModel<EntityModel<SubTopic>> getSubTopicsByTopicID(@PathVariable Long id) { 
		Long topicID = id;
		
		Topic topic = topicRepository.findById(topicID) 
			.orElseThrow(() -> new NotFoundException("Topic", topicID.toString()));
		
		List<EntityModel<SubTopic>> subTopics = subTopicRepository.findByTopic(topic)
			.stream().map(assembler::toModel).collect(Collectors.toList());
		
		return CollectionModel.of(subTopics);    
	}
	
	// Single item
	@GetMapping("/{id}")
	public EntityModel<SubTopic> getOneSubTopic(@PathVariable Long id) { 
		Long subTopicID = id; 
		SubTopic subTopic = subTopicRepository.findById(subTopicID) 
		   	.orElseThrow(() -> new NotFoundException("SubTopic", subTopicID.toString()));
	
		return assembler.toModel(subTopic);
	} 
	
	@PostMapping("")
	public ResponseEntity<?> createNewSubTopic(@Valid @RequestBody SubTopicRequest subTopicRequest) { 
		Topic topic = topicRepository.findById(subTopicRequest.getTopicID()) 
		    .orElseThrow(() -> new NotFoundException("Topic", subTopicRequest.getTopicID().toString()));
		
		SubTopic newSubTopic = new SubTopic(
			subTopicRequest.getName(),
			subTopicRequest.getNumber(),
			subTopicRequest.isFree(),
			topic); 
			 
		EntityModel<SubTopic> entityModel = assembler.toModel(subTopicRepository.save(newSubTopic));
			 
		return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.body(entityModel); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceSubTopic(
			@Valid @RequestBody SubTopicRequest subTopicRequest, @PathVariable Long id) {
		Long subTopicID = id;
		Topic topic = topicRepository.findById(subTopicRequest.getTopicID()) 
	    	.orElseThrow(() -> new NotFoundException("Topic", subTopicRequest.getTopicID().toString()));
		SubTopic newSubTopic = new SubTopic(
			subTopicRequest.getName(),
			subTopicRequest.getNumber(),
			subTopicRequest.isFree(),
			topic);
		
	    SubTopic updateSubTopic = subTopicRepository.findById(subTopicID)
	  	    .map(subTopic -> {
	  	    	subTopic.setName(newSubTopic.getName());
	  	        subTopic.setNumber(newSubTopic.getNumber());
	  	        subTopic.setFree(newSubTopic.isFree());
	  	        subTopic.setTopic(topic);
	  	        return subTopicRepository.save(subTopic); 
	  	    }) 
	  	    .orElseGet(() -> {
	  	    	newSubTopic.setSubTopicID(id);
	  	    	return subTopicRepository.save(newSubTopic); 
	  	    });
	  	    
	    EntityModel<SubTopic> entityModel = assembler.toModel(updateSubTopic);
	  	     
	    return ResponseEntity .created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
	    	.toUri()) //
	    	.body(entityModel);
	} 
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubTopic(@PathVariable Long id) {
		Long subTopicID = id;
		subTopicRepository.deleteById(subTopicID); 
		return ResponseEntity.noContent().build(); 
	}
}