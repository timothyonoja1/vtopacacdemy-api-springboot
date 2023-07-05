package com.vtopacademy.kclasses;

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
import com.vtopacademy.schools.School; 
import com.vtopacademy.schools.SchoolRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Kclass", description = "Kclass management APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/kclasses")
public class KclassesController {
	
	@Autowired
	private SchoolRepository schoolRepository; 

	@Autowired
	private KclassRepository kclassRepository;
	
	@Autowired
	private KclassModelAssembler assembler; 
	
	// Aggregate root 
	// tag::get-aggregate-root[] 
	@GetMapping("")
	public CollectionModel<EntityModel<Kclass>> getAllKclasses() {  
		List<EntityModel<Kclass>> kclasses = kclassRepository.findAll().stream() 
			.map(assembler::toModel) 
			.collect(Collectors.toList());
		return CollectionModel.of(kclasses, 
			linkTo(methodOn(KclassesController.class).getAllKclasses()) 
			.withSelfRel()); 
	} 
		
	@GetMapping("/by-schoolID/{id}")  
	public CollectionModel<EntityModel<Kclass>> getKclassesBySchoolID(@PathVariable Long id) { 
		Long schoolID = id;
		
		School school = schoolRepository.findById(schoolID) 
	    	.orElseThrow(() -> new NotFoundException("School", schoolID.toString()));
		
		
		List<EntityModel<Kclass>> kclasses = kclassRepository.findBySchool(school)
			.stream().map(assembler::toModel).collect(Collectors.toList());
		
		return CollectionModel.of(kclasses,
			linkTo(methodOn(KclassesController.class).getAllKclasses())
			.withSelfRel());   
	}
	
	
	// Single item
	@GetMapping("/{id}")
	public EntityModel<Kclass> getOneKclass(@PathVariable Long id) { 
		Long kclassID = id;
		Kclass kclass = kclassRepository.findById(kclassID) 
	    	.orElseThrow(() -> new NotFoundException("Kclass", kclassID.toString()));

	    return assembler.toModel(kclass); 
	}
	 
	@PostMapping("")
	public ResponseEntity<?> createNewKclass(@Valid @RequestBody KclassRequest kclassRequest) { 
		School school = schoolRepository.findById(kclassRequest.getSchoolID()) 
	    	.orElseThrow(() -> new NotFoundException("School", kclassRequest.getSchoolID().toString()));
		Kclass newKclass = new Kclass(
			kclassRequest.getName(),
			kclassRequest.getNumber(),
			school);
		 
		EntityModel<Kclass> entityModel = assembler.toModel(kclassRepository.save(newKclass));
		
		return ResponseEntity 
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) 
			.body(entityModel); 
	}
	 
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceKclass(@Valid @RequestBody KclassRequest kclassRequest, @PathVariable Long id) {
		Long kclassID = id;
		School school = schoolRepository.findById(kclassRequest.getSchoolID()) 
	    	.orElseThrow(() -> new NotFoundException("School", kclassRequest.getSchoolID().toString()));
		Kclass newKclass = new Kclass(
			kclassRequest.getName(),
			kclassRequest.getNumber(),
			school);
		
	    Kclass updatedKclass = kclassRepository.findById(kclassID)
	  	    .map(kclass -> {
	  	    	kclass.setName(newKclass.getName());
	  	        kclass.setNumber(newKclass.getNumber());
	  	        kclass.setSchool(school);
	  	        return kclassRepository.save(kclass);
	  	     }) 
	  	     .orElseGet(() -> {
	  	        newKclass.setKclassID(id);
	  	        return kclassRepository.save(newKclass);
	  	     });
	  	    
	    EntityModel<Kclass> entityModel = assembler.toModel(updatedKclass);
	  	    
	  	return ResponseEntity .created(entityModel.getRequiredLink(IanaLinkRelations.SELF)
	  		.toUri()) //
	  		.body(entityModel);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteKclass(@PathVariable Long id) {
		Long kclassID = id;
		kclassRepository.deleteById(kclassID); 
		return ResponseEntity.noContent().build(); 
	}
}

