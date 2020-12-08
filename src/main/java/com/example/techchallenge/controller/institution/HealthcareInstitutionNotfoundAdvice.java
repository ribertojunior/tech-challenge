package com.example.techchallenge.controller.institution;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HealthcareInstitutionNotfoundAdvice {

  @ResponseBody
  @ExceptionHandler(HealthcareInstitutionNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String institutionNotFoundHandler( HealthcareInstitutionNotFoundException e) { return e.getMessage(); }
}
