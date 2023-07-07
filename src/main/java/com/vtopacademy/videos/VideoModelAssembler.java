package com.vtopacademy.videos;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import com.vtopacademy.practicequestions.PracticeQuestionsController;

public class VideoModelAssembler implements 
	RepresentationModelAssembler<Video, EntityModel<Video>> {

	@Override
	public EntityModel<Video> toModel(Video video) { 
		
		return EntityModel.of(video, 
			linkTo(methodOn(VideosController.class) 
				.getOneVideo(video.getVideoID())).withSelfRel(),
			linkTo(methodOn(PracticeQuestionsController.class) 
				.getPracticeQuestionsByVideoID(video.getVideoID()))
				.withRel("PracticeQuestions"));
		 
	} 
 
}