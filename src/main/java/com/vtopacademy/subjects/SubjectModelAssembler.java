package com.vtopacademy.subjects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.vtopacademy.topics.TopicsController;

public class SubjectModelAssembler implements 
	RepresentationModelAssembler<Subject, EntityModel<Subject>> {

	@Override
	public EntityModel<Subject> toModel(Subject subject) { 

		return EntityModel.of(subject, 
			linkTo(methodOn(SubjectsController.class) 
				.getOneSubject(subject.getSubjectID())).withSelfRel(),
			linkTo(methodOn(TopicsController.class) 
				.getTopicsBySubjectID(subject.getSubjectID(), 0, 10))
				.withRel("topicsBySubjectID"),
			linkTo(methodOn(SubjectsController.class)
				.getAllSubjects()).withRel("subjects"));   
	} 

} 