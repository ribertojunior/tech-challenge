package com.example.techchallenge.controller.exam;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExamAdvice {

  @ResponseBody
  @ExceptionHandler(ExamException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String examHandler(ExamException e) {
    return e.getMessage();
  }
}
