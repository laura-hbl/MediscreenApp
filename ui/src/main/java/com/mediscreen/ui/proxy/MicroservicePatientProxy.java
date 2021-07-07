package com.mediscreen.ui.proxy;

import com.mediscreen.ui.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Permits to connection between Ui application and patient microservice.
 *
 * @author Laura Habdul
 */
@FeignClient(value = "patient-microservice", url = "${proxy.patient}")
public interface MicroservicePatientProxy {

    /**
     * Adds a new patient.
     *
     * @param patientDTO the patient to be added
     * @return The added patient
     */
    @PostMapping({"/add"})
    PatientDTO addPatient(final PatientDTO patientDTO);

    /**
     * Retrieves a patient based on the given id.
     *
     * @param patientId id of the patient
     * @return The patient with the given id
     */
    @GetMapping({"/get/{id}"})
    PatientDTO getPatientById(@PathVariable("id") final Integer patientId);

    /**
     * Updates a saved patient.
     *
     * @param patientId  id of the patient to be updated
     * @param patientDTO the patient to be updated
     * @return The updated patient
     */
    @PostMapping({"/update/{id}"})
    PatientDTO updatePatient(@PathVariable("id") final Integer patientId, final PatientDTO patientDTO);

    /**
     * Displays all the patient list or the list of patients whose first or last name matches the entered keyword.
     *
     * @param keyword keyword entered by the user to search for a patient
     * @return The patient list
     */
    @GetMapping({"/list"})
    List<PatientDTO> getPatientList(@RequestParam(value = "keyword", required = false) final String keyword);

    /**
     * Deletes a saved patient.
     *
     * @param patientId id of the patient to be deleted
     */
    @GetMapping({"/delete/{id}"})
    void deletePatient(@PathVariable("id") final Integer patientId);
}
