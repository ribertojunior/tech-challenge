package com.example.techchallenge.entity;

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
}
