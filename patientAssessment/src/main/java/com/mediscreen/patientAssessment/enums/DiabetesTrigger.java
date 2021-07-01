package com.mediscreen.patientAssessment.enums;

import lombok.Getter;

/**
 * Contains the different diabetes triggers.
 *
 * @author Laura Habdul
 */
@Getter
public enum DiabetesTrigger {

    HEMOGLOBIN_A1C("Hemoglobin A1C"),
    MICROALBUMIN("Microalbumin"),
    HEIGHT("Height"),
    WEIGHT("Weight"),
    SMOKER("Smoker"),
    ABNORMAL("Abnormal"),
    CHOLESTEROL("Cholesterol"),
    DIZZINESS("Dizziness"),
    RELAPSE("Relapse"),
    REACTION("Reaction"),
    ANTIBODY("Antibody");

    private String trigger;

    DiabetesTrigger(final String trigger) {
        this.trigger = trigger;
    }
}