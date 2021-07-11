package com.mediscreen.patient.dto;

import com.mediscreen.patient.constant.PatientConstraints;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

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
    @NotEmpty(message = "Last name is mandatory")
    @Length(max = PatientConstraints.LAST_NAME_MAX_SIZE, message = "The maximum length for lastName is 125 characters")
    private String lastName;

    /**
     * First name of the patient.
     */
    @NotEmpty(message = "First name is mandatory")
    @Length(max = PatientConstraints.FIRST_NAME_MAX_SIZE, message = "The maximum length for firstName is 125 characters")
    private String firstName;

    /**
     * Birth date of the patient.
     */
    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Please enter a valid birth date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /**
     * Sex of the patient.
     */
    @NotEmpty(message = "Sex is mandatory")
    @Length(max = PatientConstraints.SEX_MAX_SIZE, message = "The maximum length for sex is 1 characters")
    @Pattern(regexp = "^[M|F]{1}$", message = "Please enter character M or F")
    private String sex;

    /**
     * Address of the patient.
     */
    @Length(max = PatientConstraints.ADDRESS_MAX_SIZE, message = "The maximum length for address is 150 characters")
    private String address;

    /**
     * Phone number of the patient.
     */
    @Length(max = PatientConstraints.PHONE_MAX_SIZE, message = "Please enter a valid phone number"
    )
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
