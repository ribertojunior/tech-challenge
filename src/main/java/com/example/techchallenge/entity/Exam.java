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
public class Exam {

  private @Id @GeneratedValue Long id;
  @NonNull private Long healthcareInstitutionId;
  @NonNull private String patientName;
  @NonNull private int patientAge;
  @NonNull private String patientGender;
  @NonNull private String physicianName;
  @NonNull private String physicianCRM;
  @NonNull private String procedureName;
}
