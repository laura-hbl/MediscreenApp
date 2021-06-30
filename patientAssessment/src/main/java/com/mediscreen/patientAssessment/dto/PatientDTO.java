package com.mediscreen.patientAssessment.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * Permits the storage and retrieving data of a patient.
 *
 * @author Laura Habdul
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PatientDTO {

    /**
     * Id of the patient.
     */
    private Integer id;

    /**
     * Last name of the patient.
     */
    private String lastName;

    /**
     * First name of the patient.
     */
    private String firstName;

    /**
     * Birth date of the patient.
     */
    private LocalDate birthDate;

    /**
     * Sex of the patient.
     */
    private String sex;

    /**
     * Address of the patient.
     */
    private String address;

    /**
     * Phone number of the patient.
     */
    private String phoneNumber;
}
