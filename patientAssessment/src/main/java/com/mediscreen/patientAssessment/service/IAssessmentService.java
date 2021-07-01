package com.mediscreen.patientAssessment.service;

import com.mediscreen.patientAssessment.dto.AssessmentDTO;

/**
 * AssessmentService interface.
 *
 * @author Laura Habdul
 */
public interface IAssessmentService {

    /**
     * Retrieves the diabetes assessment of the patient with the given id.
     *
     * @param patientId id of the patient
     * @return The patient diabetes assessment
     */
    AssessmentDTO getPatientAssessment(final Integer patientId);
}
