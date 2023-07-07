package com.vtopacademy.subjects;

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

@Tag(name = "Subject", description = "Subject management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/subjects")
public class SubjectsController {
	
	@Autowired 
	private SchoolRepository schoolRepository; 

	@Autowired 
	private SubjectRepository subjectRepository;
	
	@Autowired
	private SubjectModelAssembler assembler;  
	
	// Aggregate root 
	// tag::get-aggregate-root[] 
	@GetMapping("")
	public CollectionModel<EntityModel<Subject>> getAllSubjects() {  
		List<EntityModel<Subject>> subjects = subjectRepository.findAll().stream() 
			.map(assembler::toModel)  
			.collect(Collectors.toList());
		return CollectionModel.of(subjects, 
			linkTo(methodOn(SubjectsController.class).getAllSubjects()) 
			.withSelfRel());  
	}	
	
	@GetMapping("/by-schoolID/{id}")  
	public CollectionModel<EntityModel<Subject>> getSubjectsBySchoolID(@PathVariable Long id) { 
		Long schoolID = id;
		
		School school = schoolRepository.findById(schoolID) 
			.orElseThrow(() -> new NotFoundException("School", schoolID.toString()));
		
		List<EntityModel<Subject>> subjects = subjectRepository.findBySchool(school)
			.stream().map(assembler::toModel).collect(Collectors.toList());
		
		return CollectionModel.of(subjects,
			linkTo(methodOn(SubjectsController.class).getAllSubjects())
			.withSelfRel());    
	}
	
	// Single item
	@GetMapping("/{id}")
	public EntityModel<Subject> getOneSubject(@PathVariable Long id) { 
		Long subjectID = id; 
		Subject subject = subjectRepository.findById(subjectID) 
	    	.orElseThrow(() -> new NotFoundException("Subject", subjectID.toString()));

	    return assembler.toModel(subject);
	} 
		
	@PostMapping("")
	public ResponseEntity<?> createNewSubject(@Valid @RequestBody SubjectRequest subjectRequest) { 
		School school = schoolRepository.findById(subjectRequest.getSchoolID()) 
		    .orElseThrow(() -> new NotFoundException("School", subjectRequest.getSchoolID().toString()));
		Subject newSubject = new Subject(
			subjectRequest.getName(),
			subjectRequest.getNumber(),
			school); 
			 
		EntityModel<Subject> entityModel = assembler.toModel(subjectRepository.save(newSubject));
			 
		return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.body(entityModel); 
	}
	 
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceSubject(@Valid @RequestBody SubjectRequest subjectRequest, @PathVariable Long id) {
		Long subjectID = id;
		School school = schoolRepository.findById(subjectRequest.getSchoolID()) 
	    	.orElseThrow(() -> new NotFoundException("School", subjectRequest.getSchoolID().toString()));
		Subject newSubject = new Subject(
			subjectRequest.getName(),
			subjectRequest.getNumber(),
			school);
		
	    Subject updatedSubject = subjectRepository.findById(subjectID)
	  	    .map(subject -> {
	  	    	subject.setName(newSubject.getName());
	  	        subject.setNumber(newSubject.getNumber());
	  	        subject.setSchool(school);
	  	        return subjectRepository.save(subject);
	  	    }) 
	  	    .orElseGet(() -> {
	  	    	newSubject.setSubjectID(id);
	  	    	return subjectRepository.save(newSubject);
	  	    });
	  	    
	    EntityModel<Subject> entityModel = assembler.toModel(updatedSubject);
	  	     
	    return ResponseEntity .created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
	    	.toUri()) //
	    	.body(entityModel);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
		Long subjectID = id;
		subjectRepository.deleteById(subjectID); 
		return ResponseEntity.noContent().build(); 
	}
}
