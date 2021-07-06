package com.mediscreen.patient.controller;

import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.service.IPatientService;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Creates REST endpoints for crud operations on Patient data.
 *
 * @author Laura Habdul
 * @see IPatientService
 */
@RestController
@RequestMapping("/patient")
public class PatientController {

    /**
     * PatientController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(PatientController.class);

    /**
     * IPatientService's implement class reference.
     */
    private final IPatientService patientService;

    /**
     * Constructor of class PatientController.
     * Initialize patientService.
     *
     * @param patientService IPatientService's implement class reference
     */
    @Autowired
    public PatientController(final IPatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Adds a new patient.
     *
     * @param patientDTO the patient to be added
     * @return The added patient
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO addPatient(@RequestBody final PatientDTO patientDTO) {
        LOGGER.debug("POST Request on /patient/add on patient {} {}", patientDTO.getFirstName() + "" +
                patientDTO.getLastName());

        PatientDTO patientAdded = patientService.addPatient(patientDTO);

        LOGGER.info("POST Request on /patient/add - SUCCESS");
        return patientAdded;
    }

    /**
     * Retrieves a patient based on the given id.
     *
     * @param patientId id of the patient
     * @return The patient with the given id
     */
    @GetMapping("/get/{id}")
    public PatientDTO getPatientById(@PathVariable("id") final Integer patientId) {
        LOGGER.debug("GET Request on /patient/get/{id} with id : {}", patientId);

        PatientDTO patient = patientService.getPatientById(patientId);

        LOGGER.info("GET Request on /patient/get/{id} - SUCCESS");
        return patient;
    }

    /**
     * Updates a saved patient.
     *
     * @param patientId  id of the patient to be updated
     * @param patientDTO the patient to be updated
     * @return The updated patient
     */
    @PostMapping("/update/{id}")
    public PatientDTO updatePatient(@PathVariable("id") final Integer patientId,
                                    @Valid @RequestBody final PatientDTO patientDTO) {
        LOGGER.debug("POST Request on /patient/update/{id} with id : {}", patientId);

        PatientDTO patientUpdated = patientService.updatePatient(patientId, patientDTO);

        LOGGER.info("POST Request on /patient/update/{id} - SUCCESS");
        return patientUpdated;
    }

    /**
     * Displays all the patient list or the list of patients whose first or last name matches the entered keyword.
     *
     * @param keyword keyword entered by the user to search for a patient
     * @return The patient list
     */
    @GetMapping("/list")
    public List<PatientDTO> getPatientList(@RequestParam(value = "keyword", required = false) final String keyword) {
        LOGGER.debug("GET Request on /patient/list");

        List<PatientDTO> patientList = patientService.getAllPatient(keyword);

        LOGGER.info("GET Request on /patient/list - SUCCESS");
        return patientList;
    }

    /**
     * Deletes a saved patient.
     *
     * @param patientId id of the patient to be deleted
     */
    @GetMapping("/delete/{id}")
    public void deletePatient(@PathVariable("id") final Integer patientId) {
        LOGGER.debug("GET Request on /patient/delete/{id} with id : {}", patientId);

        patientService.deletePatient(patientId);

        LOGGER.info("GET Request on /patient/delete/{id} - SUCCESS");
    }
}
