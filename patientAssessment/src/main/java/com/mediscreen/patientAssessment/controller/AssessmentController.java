package com.mediscreen.patientAssessment.controller;

import com.mediscreen.patientAssessment.dto.AssessmentDTO;
import com.mediscreen.patientAssessment.service.IAssessmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Creates REST endpoint on Patient assessment data.
 *
 * @author Laura Habdul
 * @see IAssessmentService
 */
@RequestMapping("/assess")
@RestController
public class AssessmentController {

    /**
     * AssessmentController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AssessmentController.class);

    /**
     * IAssessmentService's implement class reference.
     */
    private IAssessmentService assessmentService;

    /**
     * Constructor of class AssessmentController.
     * Initialize assessmentService.
     *
     * @param assessmentService IAssessmentService's implement class reference
     */
    @Autowired
    public AssessmentController(final IAssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    /**
     * Retrieves the diabetes assessment of the patient with the given id.
     *
     * @param patientId id of the patient
     * @return The patient diabetes assessment
     */
    @GetMapping("/{id}")
    public AssessmentDTO getPatientAssessment(@PathVariable("id") final Integer patientId) {
        LOGGER.debug("GET Request on /assess/id with patient id : {}", patientId);

        AssessmentDTO patientAssessment = assessmentService.getPatientAssessment(patientId);

        LOGGER.info("GET Request on /assess/id - SUCCESS");
        return patientAssessment;
    }
}
