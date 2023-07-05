package com.vtopacademy.schools;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
 
@Tag(name = "School", description = "School management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/schools")
public class SchoolsController {
	
	@Autowired
	private SchoolRepository schoolRepository;
	
	@Autowired
	private SchoolModelAssembler assembler; 
	
	// Aggregate root 
	// tag::get-aggregate-root[] 
	@GetMapping("")
	public CollectionModel<EntityModel<School>> getAllSchools() {
		List<EntityModel<School>> schools = schoolRepository.findAll().stream() 
			.map(assembler::toModel) 
			.collect(Collectors.toList());
		return CollectionModel.of(schools, 
			linkTo(methodOn(SchoolsController.class).getAllSchools())
			.withSelfRel()); 
	} 
	// end::get-aggregate-root[]

	@PostMapping("")
	//@PreAuthorize("hasRole('ROLE_ADMIN')")  
	public ResponseEntity<?> createNewSchool(@RequestBody School newSchool) {
		EntityModel<School> entityModel = assembler.toModel(schoolRepository.save(newSchool));
		
		return ResponseEntity .created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
			.toUri()) //
			.body(entityModel);  
	}
	 
	// Single item 
	@GetMapping("/{id}")
	public EntityModel<School> getOneSchool(@PathVariable Long id) {
	    Long schoolID = id;
	    School school = schoolRepository.findById(schoolID) 
	    	.orElseThrow(() -> new NotFoundException("School", schoolID.toString()));

	    return assembler.toModel(school);
	}  
	 
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceSchool(@RequestBody School newSchool, @PathVariable Long id) {
	    Long schoolID = id;
	    School updatedSchool = schoolRepository.findById(schoolID)
	      .map(school -> {
	        school.setName(newSchool.getName());
	        school.setNumber(newSchool.getNumber());
	        return schoolRepository.save(school);
	      })
	      .orElseGet(() -> {
	        newSchool.setSchoolID(id);
	        return schoolRepository.save(newSchool); 
	      });
	     
	    EntityModel<School> entityModel = assembler.toModel(updatedSchool);
	     
	    return ResponseEntity 
	    	.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
			.body(entityModel);
	} 
	 
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSchool(@PathVariable Long id) {
		Long schoolID = id;
		schoolRepository.deleteById(schoolID);
		return ResponseEntity.noContent().build(); 
	} 
}


