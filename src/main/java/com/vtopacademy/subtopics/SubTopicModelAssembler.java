package com.vtopacademy.subtopics;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component 
public class SubTopicModelAssembler implements 
	RepresentationModelAssembler<SubTopic, EntityModel<SubTopic>> {
	
	@Override
	public EntityModel<SubTopic> toModel(SubTopic subTopic) { 

		return EntityModel.of(subTopic,
			linkTo(methodOn(SubTopicsController.class) 
				.getOneSubTopic(subTopic.getSubTopicID()))
				.withSelfRel());   
			   
	} 
} 
