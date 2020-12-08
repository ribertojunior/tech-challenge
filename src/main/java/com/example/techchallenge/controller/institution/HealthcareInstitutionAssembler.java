package com.example.techchallenge.controller.institution;

import com.example.techchallenge.entity.HealthcareInstitution;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HealthcareInstitutionAssembler
    implements RepresentationModelAssembler<
        HealthcareInstitution, EntityModel<HealthcareInstitution>> {

  @Override
  public EntityModel<HealthcareInstitution> toModel(HealthcareInstitution entity) {
    return EntityModel.of(
        entity,
        linkTo(methodOn(HealthcareInstitutionController.class).one(entity.getId())).withSelfRel(),
        linkTo(methodOn(HealthcareInstitutionController.class).all()).withRel("institutions"));
  }
}
