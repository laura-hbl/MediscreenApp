package com.mediscreen.patientAssessment.enums;
import lombok.Getter;

/**
 * Contains the different diabetes risk levels.
 *
 * @author Laura Habdul
 */
@Getter
public enum RiskLevels {

    NONE("None"),
    BORDERLINE("Borderline"),
    IN_DANGER("In danger"),
    EARLY_ONSET("Early onset");

    private String riskLevel;

    private RiskLevels(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
