package com.example.techchallenge.controller.institution;

public class HealthcareInstitutionNotFoundException extends RuntimeException {
  public HealthcareInstitutionNotFoundException(Long id) {
    super("Healthcare Institution not found: " + id);
  }
}
