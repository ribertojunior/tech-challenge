package com.example.techchallenge.controller.institution;

import com.example.techchallenge.entity.HealthcareInstitution;
import com.example.techchallenge.repository.HealthcareInstitutionRepository;
import com.example.techchallenge.security.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.techchallenge.security.Utils.getJWTToken;
import static com.example.techchallenge.utils.Utils.removeNonNumbers;
import static com.example.techchallenge.utils.Utils.validaInstitution;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class HealthcareInstitutionController {

  private final HealthcareInstitutionRepository repository;
  private final HealthcareInstitutionAssembler assembler;

  @GetMapping("/institutions")
  CollectionModel<EntityModel<HealthcareInstitution>> all() {
    List<EntityModel<HealthcareInstitution>> institutions =
        repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
    return CollectionModel.of(
        institutions, linkTo(methodOn(HealthcareInstitutionController.class).all()).withSelfRel());
  }

  @PostMapping("/institutions")
  ResponseEntity<?> newInstitution(@RequestBody HealthcareInstitution institution) {
    try {
      if (validaInstitution(institution)) {
        if (repository.findByCnpj(institution.getCnpj()) != null
            || repository.findByCnpj(removeNonNumbers(institution.getCnpj())) != null) {
          throw new HealthcareInstitutionException(
              "CNPJ " + institution.getCnpj() + " repetition clause error");
        }
        institution.setCnpj(removeNonNumbers(institution.getCnpj()));
        institution.setPixeon(20);
        HealthcareInstitution institutionSave = repository.save(institution);
        EntityModel<HealthcareInstitution> entityModel = assembler.toModel(institutionSave);
        return ResponseEntity.created(
                linkTo(methodOn(HealthcareInstitutionController.class).one(institutionSave.getId()))
                    .withSelfRel()
                    .toUri())
            .body(entityModel);
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
          .body(
              Problem.create().withTitle("HealthcareInstitution error").withDetail(e.getMessage()));
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
        .body(Problem.create().withTitle("HealthcareInstitution with information error."));
  }

  @GetMapping("/institutions/{id}")
  EntityModel<HealthcareInstitution> one(@PathVariable Long id) {
    HealthcareInstitution institution =
        repository.findById(id).orElseThrow(() -> new HealthcareInstitutionNotFoundException(id));
    return assembler.toModel(institution);
  }

  @PutMapping("/institutions/{id}")
  ResponseEntity<?> replaceInstitution(
      @RequestBody HealthcareInstitution novoInstitution, @PathVariable Long id) {
    HealthcareInstitution healthcareInstitution = new HealthcareInstitution();
    if (validaInstitution(novoInstitution)) {
      HealthcareInstitution byCnpj =
          repository.findByCnpj(removeNonNumbers(novoInstitution.getCnpj()));
      if (byCnpj != null && !byCnpj.getId().equals(novoInstitution.getId())) {
        throw new HealthcareInstitutionException(
            "CNPJ " + novoInstitution.getCnpj() + " repetition clause error");
      }
      healthcareInstitution =
          repository
              .findById(id)
              .map(
                  institution -> {
                    institution.setCnpj(novoInstitution.getCnpj());
                    institution.setName(novoInstitution.getName());
                    return repository.save(institution);
                  })
              .orElseGet(
                  () -> {
                    novoInstitution.setId(id);
                    return repository.save(novoInstitution);
                  });
    }
    EntityModel<HealthcareInstitution> entityModel = assembler.toModel(healthcareInstitution);
    return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
        .body(entityModel);
  }

  @DeleteMapping("/institutions/{id}")
  ResponseEntity<?> deleteInstitution(@PathVariable Long id) {
    repository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/auth")
  Token login(@RequestBody HealthcareInstitution institution) {
    if (validaInstitution(institution)) {
      HealthcareInstitution byCnpj =
          repository.findByCnpj(removeNonNumbers(institution.getCnpj()));
      if (byCnpj == null) {
        throw new HealthcareInstitutionNotFoundException(null);
      }
      return new Token(getJWTToken(institution.getCnpj()));
    }
    throw new HealthcareInstitutionException("Healthcare Institution info with error");
  }


}
