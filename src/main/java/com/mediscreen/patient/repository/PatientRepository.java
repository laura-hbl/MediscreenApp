package com.mediscreen.patient.repository;

import com.mediscreen.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Patient findByLastNameAndFirstNameAndBirthDate(final String lastName, final String firstName,
                                                   final LocalDate birthDate);

    @Query("SELECT p FROM Patient p WHERE CONCAT(p.lastName, ' ', p.firstName) LIKE %?1%")
    List<Patient> findByKeyword(String keyword);
}

