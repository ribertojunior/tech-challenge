package com.example.techchallenge.controller.exam;

import com.example.techchallenge.controller.institution.HealthcareInstitutionException;
import com.example.techchallenge.entity.Exam;
import com.example.techchallenge.repository.ExamRepository;
import com.example.techchallenge.repository.HealthcareInstitutionRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.techchallenge.utils.Utils.validaExam;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class ExamController {

  private final ExamRepository repository;
  private final ExamAssembler assembler;

  private final HealthcareInstitutionRepository institutionRepository;

  @GetMapping("/exams")
  CollectionModel<EntityModel<Exam>> all() {
    List<EntityModel<Exam>> exams =
        repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
    return CollectionModel.of(exams, linkTo(methodOn(ExamController.class).all()).withSelfRel());
  }

  @PostMapping("/exams")
  ResponseEntity<?> newExam(@RequestBody Exam exam) {
    try {
      if (validaExam(exam)) {
        if (!institutionRepository.findById(exam.getHealthcareInstitutionId()).isPresent()) {
          throw new HealthcareInstitutionException("Healthcare Institution not found");
        }
        Exam examSave = repository.save(exam);
        EntityModel<Exam> entityModel = assembler.toModel(examSave);
        return ResponseEntity.created(
                linkTo(methodOn(ExamController.class).one(examSave.getId())).withSelfRel().toUri())
            .body(entityModel);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
          .body(Problem.create().withTitle("Exam error").withDetail(e.getMessage()));
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(Problem.create().withTitle("Exam with information error."));
  }

  @GetMapping("/exams/{id}")
  EntityModel<Exam> one(@PathVariable Long id) {
    Exam exam =
        repository.findById(id).orElseThrow(() -> new ExamNotFoundException(id));
    return assembler.toModel(exam);
  }

  @PutMapping("/exams/{id}")
  ResponseEntity<?> replaceExam(@RequestBody Exam novoExam, @PathVariable Long id) {
    Exam exam =
        repository
            .findById(id)
            .map(
                exam1 -> {
                  exam1.setHealthcareInstitutionId(novoExam.getHealthcareInstitutionId());
                  exam1.setPatientAge(novoExam.getPatientAge());
                  exam1.setPatientGender(novoExam.getPatientGender());
                  exam1.setPatientName(novoExam.getPatientName());
                  exam1.setPhysicianCRM(novoExam.getPhysicianCRM());
                  exam1.setPhysicianName(novoExam.getPhysicianName());
                  exam1.setProcedureName(novoExam.getProcedureName());
                  return repository.save(exam1);
                })
            .orElseGet(
                () -> {
                  novoExam.setId(id);
                  return repository.save(novoExam);
                });
    EntityModel<Exam> entityModel = assembler.toModel(exam);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping("/exams/{id}")
  ResponseEntity<?> deleteExam(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
