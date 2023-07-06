package com.vtopacademy.topics;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public class TopicModelAssembler implements 
	RepresentationModelAssembler<Topic, EntityModel<Topic>> {

	@Override
	public EntityModel<Topic> toModel(Topic topic) { 

		return EntityModel.of(topic, 
			linkTo(methodOn(TopicsController.class) 
				.getOneTopic(topic.getTopicID())).withSelfRel());
			 
	} 

}