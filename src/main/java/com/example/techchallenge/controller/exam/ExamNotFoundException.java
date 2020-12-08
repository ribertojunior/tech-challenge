package com.example.techchallenge.controller.exam;

public class ExamNotFoundException extends RuntimeException{
  public ExamNotFoundException(Long id) {
    super("Exam not found: " + id);
  }
}
