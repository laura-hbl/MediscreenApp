package com.mediscreen.patient.repository;

import com.mediscreen.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * JPA repository interface that provides methods that permit interaction with database patients table.
 *
 * @author Laura Habdul
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    /**
     * Finds a patient by the lastName, firstName and birthDate.
     *
     * @param lastName  last name of the patient
     * @param firstName first name of the patient
     * @param birthDate birth date of the patient
     * @return The patient found
     */
    Patient findByLastNameAndFirstNameAndBirthDate(final String lastName, final String firstName,
                                                   final LocalDate birthDate);

    /**
     * Finds the list of patients whose first or last name matches the entered keyword.
     *
     * @param keyword username of the user
     * @return The user found
     */
    @Query("SELECT p FROM Patient p WHERE CONCAT(p.lastName, ' ', p.firstName) LIKE %?1%")
    List<Patient> findByKeyword(String keyword);
}
