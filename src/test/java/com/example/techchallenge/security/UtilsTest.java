package com.example.techchallenge.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

  @Test
  void validaToken() {
    int j;
    for (int i = 0; i< 1000; i++) {
      j = (int) (Math.random() * 1000);
      assertTrue(Utils.validaToken(String.valueOf(j), Utils.getJWTToken(String.valueOf(j))));
    }
  }
}