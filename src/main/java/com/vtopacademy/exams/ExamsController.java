package com.vtopacademy.exams;

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
import com.vtopacademy.schools.School;
import com.vtopacademy.schools.SchoolRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "exams", description = "Exam management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/exams")
public class ExamsController {
	
	@Autowired
	private ExamRepository examRepository;
	
	@Autowired
	private SchoolRepository schoolRepository;
	
	@Autowired
	private ExamModelAssembler assembler;
	
	// Aggregate root 
	// tag::get-aggregate-root[] 
	@GetMapping("")
	public CollectionModel<EntityModel<Exam>> getAllExams() {  
		List<EntityModel<Exam>> exams = examRepository.findAll().stream() 
			.map(assembler::toModel) 
			.collect(Collectors.toList());
		return CollectionModel.of(exams, 
			linkTo(methodOn(ExamsController.class).getAllExams()) 
			.withSelfRel()); 
	}	
	
	@GetMapping("/by-schoolID/{id}")  
	public CollectionModel<EntityModel<Exam>> getExamsBySchoolID(@PathVariable Long id) { 
		Long schoolID = id;
		
		School school = schoolRepository.findById(schoolID) 
	    	.orElseThrow(() -> new NotFoundException("School", schoolID.toString()));
		
		
		List<EntityModel<Exam>> exams = examRepository.findBySchool(school)
			.stream().map(assembler::toModel).collect(Collectors.toList());
		
		return CollectionModel.of(exams,
			linkTo(methodOn(ExamsController.class).getAllExams())
			.withSelfRel());  
	}
	
	// Single item
	@GetMapping("/{id}")
	public EntityModel<Exam> getOneExam(@PathVariable Long id) { 
		Long examID = id;
		Exam exam = examRepository.findById(examID) 
	    	.orElseThrow(() -> new NotFoundException("Exam", examID.toString()));

	    return assembler.toModel(exam);  
	}
	 
	@PostMapping("")
	public ResponseEntity<?> createNewExam(@Valid @RequestBody ExamRequest examRequest) { 
		School school = schoolRepository.findById(examRequest.getSchoolID()) 
			.orElseThrow(() -> new NotFoundException("School", examRequest.getSchoolID().toString()));
		Exam newExam = new Exam(
			examRequest.getName(),
			examRequest.getNumber(),
			school);
			 
		EntityModel<Exam> entityModel = assembler.toModel(examRepository.save(newExam));
			
		return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.body(entityModel); 
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceExam(@Valid @RequestBody ExamRequest examRequest, @PathVariable Long id) {
		Long examID = id;
		School school = schoolRepository.findById(examRequest.getSchoolID()) 
	    	.orElseThrow(() -> new NotFoundException("School", examRequest.getSchoolID().toString()));
		Exam newExam = new Exam(
			examRequest.getName(),
			examRequest.getNumber(),
			school);
		
	    Exam updatedExam = examRepository.findById(examID)
	  	    .map(exam -> {
	  	    	exam.setName(newExam.getName());
	  	        exam.setNumber(newExam.getNumber());
	  	        exam.setSchool(school);
	  	        return examRepository.save(exam);
	  	     }) 
	  	     .orElseGet(() -> {
	  	        newExam.setExamID(id);
	  	        return examRepository.save(newExam);
	  	     });
	  	    
	    EntityModel<Exam> entityModel = assembler.toModel(updatedExam);
	  	    
	  	return ResponseEntity .created(entityModel.getRequiredLink(IanaLinkRelations.SELF) 
	  		.toUri()) //
	  		.body(entityModel);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteKclass(@PathVariable Long id) {
		Long examID = id;
		examRepository.deleteById(examID); 
		return ResponseEntity.noContent().build(); 
	}
}
