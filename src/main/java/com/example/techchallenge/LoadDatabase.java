package com.example.techchallenge;

import com.example.techchallenge.entity.Exam;
import com.example.techchallenge.entity.HealthcareInstitution;
import com.example.techchallenge.repository.ExamRepository;
import com.example.techchallenge.repository.HealthcareInstitutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

  @Bean
  CommandLineRunner initDatabase(ExamRepository examRepository, HealthcareInstitutionRepository healthcareInstitutionRepository) {
    return args -> {
      HealthcareInstitution institution = healthcareInstitutionRepository.save(
          new HealthcareInstitution("Clínica Buenas", "69.214.653/0001-70")
      );
      log.info("Preloading " + institution);
      Exam exam = examRepository.save(
          new Exam(institution.getId(), "Eu", 33, "Masculino", "Médico Bom e Barato", "1254664/SP", "Raio x")
      );
      log.info("Preloading " + exam);
    };
  }
}
