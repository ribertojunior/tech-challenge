package com.example.techchallenge.repository;

import com.example.techchallenge.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
  List<Exam> findByHealthcareInstitutionId(Long id);
  List<Exam> findByPhysicianCRM(String crm);
  List<Exam> findByPatientName(String patientName);
  List<Exam> findByProcedureName(String procedureName);

}
