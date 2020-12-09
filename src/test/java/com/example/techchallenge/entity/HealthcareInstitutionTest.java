package com.example.techchallenge.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HealthcareInstitutionTest {

  @Test
  void chargePixeon() {
    HealthcareInstitution institution =
        new HealthcareInstitution("Clínica Buenas", "69214653000170", 20);
    int j, pixeon;
    for (int i = 0; i < 100; i++) {
      j = (int) (Math.random() * 100);
      pixeon = institution.getPixeon();
      System.out.println("Charging " + j + " pixeon(s) of " + pixeon);
      try {
        institution.chargePixeon(j);
      } catch (Exception ignore) {
        System.out.println("Not enough pixeons");
      }
      assertTrue(
          j <= pixeon ? institution.getPixeon() == pixeon - j : institution.getPixeon() == pixeon);
    }
  }
}
