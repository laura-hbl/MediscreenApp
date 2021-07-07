package com.mediscreen.ui.dto;

import lombok.*;

/**
 * Permits the storage and retrieving data of a patient diabetes assessment.
 *
 * @author Laura Habdul
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class AssessmentDTO {

    /**
     * The patient whose risk for diabetes is being assessed.
     */
    private PatientDTO patientDTO;

    /**
     * The patient's risk levels for diabetes.
     */
    private String diabetesRiskLevel;
}
