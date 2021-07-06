package com.mediscreen.patient.dto;

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

    /**
     * Constructor of class PatientDTO.
     * Initialize lastName, firstName, birthDate, sex, address and phoneNumber.
     *
     * @param lastName    lastName of the patient
     * @param firstName   firstName of the patient
     * @param birthDate   birthDate of the patient
     * @param sex         sex of the patient
     * @param address     address of the patient
     * @param phoneNumber phone number of the patient
     */
    public PatientDTO(final String lastName, final String firstName, final LocalDate birthDate, final String sex,
                      final String address, final String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
