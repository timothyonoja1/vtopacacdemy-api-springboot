package com.vtopacademy.practicequestions;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PracticeQuestionModelAssembler implements 
	RepresentationModelAssembler<PracticeQuestion, EntityModel<PracticeQuestion>> {

	@Override
	public EntityModel<PracticeQuestion> toModel(PracticeQuestion practiceQuestion) { 

		return EntityModel.of(practiceQuestion, 
			linkTo(methodOn(PracticeQuestionsController.class) 
				.getOnePracticeQuestion(practiceQuestion.getId())).withSelfRel(),
			linkTo(methodOn(PracticeQuestionsController.class) 
				.getPracticeQuestionsByVideoID(practiceQuestion.getVideoID())).withRel("schools")
			); 
	}

} 
