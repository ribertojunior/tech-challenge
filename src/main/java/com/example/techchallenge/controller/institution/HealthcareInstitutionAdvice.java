package com.example.techchallenge.controller.institution;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HealthcareInstitutionAdvice {

  @ResponseBody
  @ExceptionHandler(HealthcareInstitutionException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String institutionHandler(HealthcareInstitutionException e) {
    return e.getMessage();
  }
}
