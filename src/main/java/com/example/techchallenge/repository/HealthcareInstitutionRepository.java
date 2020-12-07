package com.example.techchallenge.repository;

import com.example.techchallenge.entity.HealthcareInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthcareInstitutionRepository extends JpaRepository<HealthcareInstitution, Long> {
  HealthcareInstitution findByCnpj(String cnpj);
}
