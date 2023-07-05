package com.vtopacademy.exams;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.vtopacademy.subjects.SubjectsController;

@Component 
public class ExamModelAssembler implements 
	RepresentationModelAssembler<Exam, EntityModel<Exam>> { 
	
	@Override
	public EntityModel<Exam> toModel(Exam exam) { 

		return EntityModel.of(exam, 
			linkTo(methodOn(ExamsController.class) 
				.getOneExam(exam.getExamID())).withSelfRel(),
			linkTo(methodOn(SubjectsController.class) 
				.getSubjectsBySchoolID(exam.getSchool().getSchoolID()))
				.withRel("subjects"), 
			linkTo(methodOn(ExamsController.class)
				.getAllExams()).withRel("exams")
			);
	}

}
  