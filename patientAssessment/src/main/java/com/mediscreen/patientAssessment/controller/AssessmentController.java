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

@RequestMapping("/assess")
@RestController
public class AssessmentController {

    private static final Logger LOGGER = LogManager.getLogger(AssessmentController.class);

    private IAssessmentService assessmentService;

    @Autowired
    public AssessmentController(final IAssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/{id}")
    public AssessmentDTO getPatientAssessment(@PathVariable("id") final Integer patientId) {

        return assessmentService.getPatientAssessment(patientId);
    }
}
