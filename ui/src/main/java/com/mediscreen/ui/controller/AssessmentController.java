package com.mediscreen.ui.controller;

import com.mediscreen.ui.proxy.MicroserviceAssessProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Creates endpoint on patient diabetes assessment data.
 *
 * @author Laura Habdul
 */
@Controller
public class AssessmentController {

    /**
     * AssessmentController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AssessmentController.class);

    /**
     * MicroserviceAssessProxy's implement class reference.
     */
    private final MicroserviceAssessProxy assessProxy;

    /**
     * Constructor of class AssessmentController.
     * Initialize assessProxy.
     *
     * @param assessProxy MicroserviceAssessProxy's implement class reference
     */
    @Autowired
    public AssessmentController(final MicroserviceAssessProxy assessProxy) {
        this.assessProxy = assessProxy;
    }

    /**
     * Retrieves the diabetes assessment of the patient with the given id.
     *
     * @param patientId id of the patient
     * @param model     makes AssessmentDTO object and patientId available to assessment/assess HTML page
     * @return The reference to the assessment/assess HTML page
     */
    @GetMapping({"/assess/{id}"})
    public String getPatientAssessment(@PathVariable("id") final Integer patientId, final Model model) {
        LOGGER.debug("GET Request on /assess/{id} with patient id : {}", patientId);

        model.addAttribute("patientId", patientId);
        model.addAttribute("assessmentDTO", this.assessProxy.getPatientAssessment(patientId));

        LOGGER.info("GET Request on /assess/{id} - SUCCESS");
        return "assessment/assess";
    }
}
