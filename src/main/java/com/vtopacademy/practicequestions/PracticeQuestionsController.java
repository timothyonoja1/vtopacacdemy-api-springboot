package com.vtopacademy.practicequestions;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PracticeQuestion", description = "PracticeQuestion management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/practice-questions")
public class PracticeQuestionsController {
	
	@Autowired
	private PracticeQuestionService practiceQuestionService;
	@Autowired
	private PracticeQuestionModelAssembler assembler; 
 
	@GetMapping("/by-videoID/{id}")  
	public CollectionModel<EntityModel<PracticeQuestion>> 
		getPracticeQuestionsByVideoID(@PathVariable Long id) { 
		Long videoID = id; 
		
		List<EntityModel<PracticeQuestion>> practiceQuestions 
			= practiceQuestionService.getPracticeQuestions(videoID)
				.stream().map(assembler::toModel).collect(Collectors.toList()); 
		
		return CollectionModel.of(practiceQuestions,
			linkTo(methodOn(PracticeQuestionsController.class)
				.getPracticeQuestionsByVideoID(id))
				.withSelfRel()); 
	} 
	
	@PostMapping("") 
	public ResponseEntity<?> createNewPracticeQuestion( 
			@RequestBody PracticeQuestion practiceQuestion) { 
		 
		EntityModel<PracticeQuestion> entityModel = assembler.toModel(
			practiceQuestionService.savePracticeQuestion(practiceQuestion));
		
		return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.body(entityModel); 
	} 
	
	@PutMapping("/{id}")
	public ResponseEntity<?> replacePracticeQuestion(
			@RequestBody PracticeQuestion newPracticeQuestion, @PathVariable String id) { 
		PracticeQuestion result = practiceQuestionService
			.updatePracticeQuestion(newPracticeQuestion);
			    
		EntityModel<PracticeQuestion> entityModel = assembler.toModel(result); 
			     
		return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.body(entityModel);
	}
	
	@DeleteMapping("/videoID/{videoID}/number/{number}") 
	public ResponseEntity<?> deletePracticeQuestion(
			@PathVariable Long videoID, @PathVariable int number) {
		practiceQuestionService.deletePracticeQuestion(videoID, number);
		return ResponseEntity.noContent().build(); 
	}
	 
}