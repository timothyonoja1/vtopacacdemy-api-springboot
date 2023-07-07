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

import com.vtopacademy.NotFoundException;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "PracticeQuestion", description = "PracticeQuestion management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/practice-questions")
public class PracticeQuestionsController {
	
	@Autowired
	private PracticeQuestionRepository practiceQuestionRepository;
	
	@Autowired
	private PracticeQuestionModelAssembler assembler; 
 
	@GetMapping("/by-videoID/{id}")  
	public CollectionModel<EntityModel<PracticeQuestion>> 
		getPracticeQuestionsByVideoID(@PathVariable Long id) { 
		Long videoID = id; 
		
		List<EntityModel<PracticeQuestion>> practiceQuestions 
			= practiceQuestionRepository.findByVideoID(videoID)
				.stream().map(assembler::toModel).collect(Collectors.toList()); 
		
		return CollectionModel.of(practiceQuestions,
			linkTo(methodOn(PracticeQuestionsController.class)
				.getPracticeQuestionsByVideoID(id))
				.withSelfRel()); 
	} 
	
	@GetMapping("/{id}") 
	public EntityModel<PracticeQuestion> getOnePracticeQuestion(@PathVariable String id) { 
		PracticeQuestion practiceQuestion = practiceQuestionRepository.findById(id) 
	    	.orElseThrow(() -> new NotFoundException("PracticeQuestion", id));

	    return assembler.toModel(practiceQuestion); 
	}
	
	@PostMapping("") 
	public ResponseEntity<?> createNewPracticeQuestion(
			@RequestBody PracticeQuestion practiceQuestion) { 
		 
		EntityModel<PracticeQuestion> entityModel 
			= assembler.toModel(practiceQuestionRepository.save(practiceQuestion));
		
		return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.body(entityModel); 
	} 
	
	@PutMapping("/{id}")
	public ResponseEntity<?> replacePracticeQuestion(
			@RequestBody PracticeQuestion newPracticeQuestion, @PathVariable String id) { 
		PracticeQuestion updatedPracticeQuestion = practiceQuestionRepository.findById(id)
			.map(practiceQuestion -> {
				practiceQuestion.setVideoID(newPracticeQuestion.getVideoID());
				practiceQuestion.setExamID(newPracticeQuestion.getExamID());
				practiceQuestion.setNumber(newPracticeQuestion.getNumber());
				practiceQuestion.setQuestion(newPracticeQuestion.getQuestion()); 
				practiceQuestion.setOptionA(newPracticeQuestion.getOptionA());
				practiceQuestion.setOptionB(newPracticeQuestion.getOptionB());
				practiceQuestion.setOptionC(newPracticeQuestion.getOptionC());
				practiceQuestion.setOptionD(newPracticeQuestion.getOptionD());
				practiceQuestion.setCorrectOption(newPracticeQuestion.getCorrectOption());
			    return practiceQuestionRepository.save(practiceQuestion);
			})
			.orElseGet(() -> {
			    newPracticeQuestion.setId(id);
			    return practiceQuestionRepository.save(newPracticeQuestion); 
			});
			    
			EntityModel<PracticeQuestion> entityModel 
			    	= assembler.toModel(updatedPracticeQuestion); 
			     
			return ResponseEntity 
						.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
					    .body(entityModel);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePracticeQuestion(@PathVariable String id) {
		practiceQuestionRepository.deleteById(id);
		return ResponseEntity.noContent().build(); 
	}
	 
}