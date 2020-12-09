package com.example.techchallenge.entity;

import com.example.techchallenge.controller.institution.HealthcareInstitutionException;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class HealthcareInstitution {
  private @Id @GeneratedValue Long id;
  @NonNull private String name;
  @NonNull private String cnpj;
  private int pixeon;

  public HealthcareInstitution(@NonNull String name, @NonNull String cnpj, int pixeon) {
    this.name = name;
    this.cnpj = cnpj;
    this.pixeon = pixeon;
  }

  public void chargePixeon(int price) {
    if (this.pixeon - price >= 0) {
      setPixeon( this.pixeon - price);
      }
    else {
      throw new HealthcareInstitutionException("Not enough Pixeon Coins");
    }
  }
}
