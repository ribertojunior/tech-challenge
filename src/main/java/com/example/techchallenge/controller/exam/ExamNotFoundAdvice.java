package com.example.techchallenge.controller.exam;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExamNotFoundAdvice {

  @ResponseBody
  @ExceptionHandler(ExamNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String examNotFound(ExamNotFoundException e) {
    return e.getMessage();
  }
}
