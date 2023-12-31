package com.vtopacademy.schools;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import com.vtopacademy.kclasses.KclassesController;
import com.vtopacademy.exams.ExamsController;

@Component
public class SchoolModelAssembler implements 
	RepresentationModelAssembler<School, EntityModel<School>> {

	@Override
	public EntityModel<School> toModel(School school) { 
	  
		return EntityModel.of(school, 
			linkTo(methodOn(SchoolsController.class)
				.getOneSchool(school.getSchoolID())).withSelfRel(),
			linkTo(methodOn(KclassesController.class)
				.getKclassesBySchoolID(school.getSchoolID()))
				.withRel("kclasses"), 
			linkTo(methodOn(ExamsController.class)
				.getExamsBySchoolID(school.getSchoolID()))
				.withRel("exams"), 
			linkTo(methodOn(SchoolsController.class)
				.getAllSchools()).withRel("schools")
			);
	}
	  
}
    
