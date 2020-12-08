package com.example.techchallenge.controller.exam;

import com.example.techchallenge.entity.Exam;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ExamAssembler implements RepresentationModelAssembler<Exam, EntityModel<Exam>> {

  @Override
  public EntityModel<Exam> toModel(Exam entity) {
    return EntityModel.of(
        entity,
        linkTo(methodOn(ExamController.class).one(entity.getId())).withSelfRel(),
        linkTo(methodOn(ExamController.class).all()).withRel("exams"));
  }
}
