package com.example.techchallenge.controller.exam;

public class ForbiddenException extends RuntimeException{
  public ForbiddenException(String s) {
    super(s);
  }
}
