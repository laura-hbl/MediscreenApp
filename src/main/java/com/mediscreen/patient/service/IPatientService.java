package com.mediscreen.patient.service;

import com.mediscreen.patient.dto.PatientDTO;

import java.util.List;

/**
 * PatientService interface.
 *
 * @author Laura Habdul
 */
public interface IPatientService {

    /**
     * Registers a new patient in database.
     *
     * @param patientDTO the patient to be registered
     * @return The patient saved converted to an PatientDTO object
     */
    PatientDTO addPatient(final PatientDTO patientDTO);

    /**
     * Updates a patient based on the given id.
     *
     * @param patientId  id of the patient to be updated
     * @param patientDTO the patient to be updated
     * @return The patient updated converted to an PatientDTO object
     */
    PatientDTO updatePatient(final int patientId, final PatientDTO patientDTO);

    /**
     * Retrieves a patient based on the given id.
     *
     * @param patientId id of the patient to be found
     * @return The patient retrieved converted to an PatientDTO object
     */
    PatientDTO getPatientById(final int patientId);

    /**
     * Deletes a patient based on the given id.
     *
     * @param patientId id of the patient to be deleted
     */
    void deletePatient(final int patientId);

    /**
     * Retrieves all the patient list or the list of patients whose first or last name matches the entered keyword.
     *
     * @param keyword keyword entered by the user to search for a patient
     * @return The patient list
     */
    List<PatientDTO> getAllPatient(final String keyword);
}
