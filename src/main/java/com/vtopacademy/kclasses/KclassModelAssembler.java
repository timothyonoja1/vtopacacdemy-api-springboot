package com.vtopacademy.kclasses;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.vtopacademy.subjects.SubjectsController; 

@Component
public class KclassModelAssembler implements 
	RepresentationModelAssembler<Kclass, EntityModel<Kclass>> {

	@Override
	public EntityModel<Kclass> toModel(Kclass kclass) { 

		return EntityModel.of(kclass, 
			linkTo(methodOn(KclassesController.class) 
				.getOneKclass(kclass.getKclassID())).withSelfRel(),
			linkTo(methodOn(SubjectsController.class) 
				.getSubjectsBySchoolID(kclass.getSchool().getSchoolID()))
				.withRel("subjects"), 
			linkTo(methodOn(KclassesController.class)
				.getAllKclasses()).withRel("kclasses")
			); 
	}

}
