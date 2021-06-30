package com.mediscreen.patientAssessment.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class AssessmentDTO {

    private PatientDTO patientDTO;

    private String diabetesRiskLevel;
}
