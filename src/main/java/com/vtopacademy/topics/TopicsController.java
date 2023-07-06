package com.vtopacademy.topics;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtopacademy.NotFoundException;
import com.vtopacademy.kclasses.Kclass;
import com.vtopacademy.kclasses.KclassRepository;
import com.vtopacademy.subjects.Subject;
import com.vtopacademy.subjects.SubjectRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Topic", description = "Topic management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/topics")
public class TopicsController { 
	
	@Autowired 
	private TopicRepository topicRepository;
	
	@Autowired 
	private KclassRepository kclassRepository;
	
	@Autowired 
	private SubjectRepository subjectRepository;
	
	@Autowired
	private TopicModelAssembler assembler;   
	
	@GetMapping("/by-kclassID/{kclassID}/by-subjectID/{subjectID}")  
	public CollectionModel<EntityModel<Topic>> getTopicsByKclassIDAndSubjectID(
			@PathVariable Long kclassID, @PathVariable Long subjectID,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {   
		
		Kclass kclass = kclassRepository.findById(kclassID) 
			.orElseThrow(() -> new NotFoundException("Kclass", kclassID.toString()));
		
		Subject subject = subjectRepository.findById(subjectID)  
				.orElseThrow(() -> new NotFoundException("Subject", subjectID.toString()));
		
		Pageable paging = PageRequest.of(page, size);
		Page<Topic> pageTopics = topicRepository
				.findByKclassAndSubject(kclass, subject, paging);
				
		List<EntityModel<Topic>> topics = pageTopics.getContent().stream() 
			.map(assembler::toModel)  
			.collect(Collectors.toList());
		
		if (pageTopics.getNumber() >= (pageTopics.getTotalElements() - 1)) {
			return CollectionModel.of(topics); 
		}
		return CollectionModel.of(topics, 
			linkTo(methodOn(TopicsController.class)
				.getTopicsByKclassIDAndSubjectID(
					kclassID, subjectID, page + 1, size)) 
				.withRel("next")); 
	}
	
	@GetMapping("/by-subjectID/{subjectID}")  
	public CollectionModel<EntityModel<Topic>> getTopicsBySubjectID(
			@PathVariable Long subjectID,
			@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {    
		
		Subject subject = subjectRepository.findById(subjectID)  
			.orElseThrow(() -> new NotFoundException("Subject", subjectID.toString()));
		 
		Pageable paging = PageRequest.of(page, size);
		Page<Topic> pageTopics = topicRepository
				.findBySubject(subject, paging);
				
		List<EntityModel<Topic>> topics = pageTopics.getContent().stream() 
			.map(assembler::toModel)  
			.collect(Collectors.toList());
		
		if (pageTopics.getNumber() >= (pageTopics.getTotalElements() - 1)) {
			return CollectionModel.of(topics); 
		}
		return CollectionModel.of(topics, 
			linkTo(methodOn(TopicsController.class)
				.getTopicsBySubjectID(subjectID, page + 1, size)) 
				.withRel("next")); 
	}

	// Single item
	@GetMapping("/{id}")
	public EntityModel<Topic> getOneTopic(@PathVariable Long id) { 
		Long topicID = id; 
		Topic topic = topicRepository.findById(topicID) 
	    	.orElseThrow(() -> new NotFoundException("Topic", topicID.toString()));

	    return assembler.toModel(topic);
	    
	}
		
	@PostMapping("")
	public ResponseEntity<?> createNewTopic(@Valid @RequestBody TopicRequest topicRequest) { 
		Kclass kclass = kclassRepository.findById(topicRequest.getKclassID()) 
		    .orElseThrow(() -> new NotFoundException("Kclass", topicRequest.getKclassID().toString()));
		
		Subject subject = subjectRepository.findById(topicRequest.getSubjectID()) 
			    .orElseThrow(() -> new NotFoundException("Subject", topicRequest.getSubjectID().toString()));
			
		Topic newTopic = new Topic(
			topicRequest.getName(),
			topicRequest.getNumber(),
			kclass, subject); 
			 
		EntityModel<Topic> entityModel = assembler.toModel(topicRepository.save(newTopic));
			 
		return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.body(entityModel); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceSubject(
			@Valid @RequestBody TopicRequest topicRequest, @PathVariable Long id) {
		Long topicID = id;
		Kclass kclass = kclassRepository.findById(topicRequest.getKclassID()) 
			.orElseThrow(() -> new NotFoundException("Kclass", topicRequest.getKclassID().toString()));
			
		Subject subject = subjectRepository.findById(topicRequest.getSubjectID()) 
			.orElseThrow(() -> new NotFoundException("Subject", topicRequest.getSubjectID().toString()));
				
		Topic newTopic = new Topic(
			topicRequest.getName(),
			topicRequest.getNumber(),
			kclass, subject); 
		
	    Topic updatedTopic = topicRepository.findById(topicID)
	  	    .map(topic -> {
	  	    	topic.setName(newTopic.getName());
	  	        topic.setNumber(newTopic.getNumber());
	  	        topic.setKclass(kclass);
	  	        topic.setSubject(subject);
	  	        return topicRepository.save(topic);
	  	    }) 
	  	    .orElseGet(() -> {
	  	    	newTopic.setTopicID(id);
	  	    	return topicRepository.save(newTopic);
	  	    });
	  	    
	    EntityModel<Topic> entityModel = assembler.toModel(updatedTopic);
	  	     
	    return ResponseEntity .created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
	    	.toUri()) //
	    	.body(entityModel); 
	}
	
	@DeleteMapping("/{id}") 
	public ResponseEntity<?> deleteTopic(@PathVariable Long id) {
		Long topicID = id;
		topicRepository.deleteById(topicID); 
		return ResponseEntity.noContent().build(); 
	}
}
