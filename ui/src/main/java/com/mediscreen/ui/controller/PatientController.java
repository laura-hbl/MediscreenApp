package com.mediscreen.ui.controller;

import com.mediscreen.ui.dto.PatientDTO;
import com.mediscreen.ui.proxy.MicroservicePatientProxy;
import feign.Param;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Creates endpoints for crud operations on Patient data.
 *
 * @author Laura Habdul
 */
@Controller
@RequestMapping({"/patient"})
public class PatientController {

    /**
     * PatientController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(PatientController.class);

    /**
     * MicroservicePatientProxy's implement class reference.
     */
    private MicroservicePatientProxy patientProxy;

    /**
     * Constructor of class PatientController.
     * Initialize patientProxy.
     *
     * @param patientProxy MicroservicePatientProxy's implement class reference
     */
    @Autowired
    public PatientController(final MicroservicePatientProxy patientProxy) {
        this.patientProxy = patientProxy;
    }

    /**
     * Displays form for adding a new patient.
     *
     * @param model makes a new PatientDTO object available to patient/add HTML page
     * @return The reference to the patient/add HTML page
     */
    @GetMapping({"/add"})
    public String addPatientForm(final Model model) {
        LOGGER.debug("GET Request on /patient/add");

        model.addAttribute("patientDTO", new PatientDTO());

        LOGGER.info("GET Request on /patient/add - SUCCESS");
        return "patient/add";
    }

    /**
     * Adds a new patient.
     *
     * @param patientDTO the patient to be added
     * @param result     holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the patient/add HTML page if result has errors. Else, redirects to /patient/list endpoint
     */
    @PostMapping({"/validate"})
    public String validate(@Valid final PatientDTO patientDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /patient/validate on patient {} {}", patientDTO.getFirstName()
                + "" + patientDTO.getLastName());

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "patient/add";
        } else {
            this.patientProxy.addPatient(patientDTO);
            LOGGER.info("POST Request on /patient/validate  - SUCCESS");
            return "redirect:/patient/list";
        }
    }

    /**
     * Displays a form to update an existing patient.
     *
     * @param patientId id of the patient to be updated
     * @param model     makes PatientDTO object available to patient/update HTML page
     * @return The reference to the patient/update HTML page
     */
    @GetMapping({"/update/{id}"})
    public String showUpdateForm(@PathVariable("id") final Integer patientId, final Model model) {
        LOGGER.debug("GET Request on /patient/update/{id} with id : {}", patientId);

        PatientDTO patient = this.patientProxy.getPatientById(patientId);
        model.addAttribute("patientDTO", patient);

        LOGGER.info("GET Request on /patient/update/{id} - SUCCESS");
        return "patient/update";
    }

    /**
     * Updates a saved patient.
     *
     * @param patientId  id of the patient to be updated
     * @param patientDTO the patient to be updated
     * @param result     holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the patient/update HTML page if result has errors. Else, redirects to /patient/list endpoint
     */
    @PostMapping({"/update/{id}"})
    public String updateUser(@PathVariable("id") final Integer patientId, @Valid final PatientDTO patientDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /patient/update/{id} with id : {}", patientId);

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "patient/update";
        } else {
            this.patientProxy.updatePatient(patientId, patientDTO);
            LOGGER.info("POST Request on /patient/update/{id} - SUCCESS");
            return "redirect:/patient/list";
        }
    }

    /**
     * Displays all the patient list or the list of patients whose first or last name matches the entered keyword.
     *
     * @param model   makes patient list objects available to patient/list HTML page
     * @param keyword keyword entered by the user to search for a patient
     * @return The reference to the patient/list HTML page
     */
    @GetMapping({"/list"})
    public String getPatientList(final Model model, @Param("keyword") final String keyword) {
        LOGGER.debug("GET Request on /patient/list");

        model.addAttribute("patients", this.patientProxy.getPatientList(keyword));
        model.addAttribute("keyword", keyword);

        LOGGER.info("GET Request on /patient/list - SUCCESS");
        return "patient/list";
    }

    /**
     * Deletes a saved patient.
     *
     * @param patientId id of the patient to be deleted
     * @return The redirect to /patient/list endpoint
     */
    @GetMapping({"/delete/{id}"})
    public String deletePatient(@PathVariable("id") final Integer patientId) {
        LOGGER.debug("GET Request on /patient/delete/{id} with id : {}", patientId);

        this.patientProxy.deletePatient(patientId);

        LOGGER.info("GET Request on /patient/delete/{id} - SUCCESS");
        return "redirect:/patient/list";
    }
}
