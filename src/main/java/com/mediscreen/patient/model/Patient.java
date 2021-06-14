package com.mediscreen.patient.model;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Permits the storage and retrieving data of a patient.
 *
 * @author Laura Habdul
 */
@Entity
@Table(name = "patients")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class Patient {

    /**
     * Id of the patient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /**
     * Last name of the patient.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * First name of the patient.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Birth date of the patient.
     */
    @Column(name = "date_of_birth")
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
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Constructor of class Patient.
     * Initialize lastName, firstName, birthDate, sex, address and phoneNumber.
     *
     * @param lastName    lastName of the patient
     * @param firstName   firstName of the patient
     * @param birthDate   birthDate of the patient
     * @param sex         sex of the patient
     * @param address     address of the patient
     * @param phoneNumber phone number of the patient
     */
    public Patient(final String lastName, final String firstName, final LocalDate birthDate, final String sex,
                   final String address, final String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
