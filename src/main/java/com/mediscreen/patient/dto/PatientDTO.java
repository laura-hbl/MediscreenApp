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

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class PatientDTO {

    private Integer id;

    @NotEmpty(message = "Last name is mandatory")
    @Length(max = PatientConstraints.LAST_NAME_MAX_SIZE, message = "The maximum length for lastName is 125 characters")
    private String lastName;

    @NotEmpty(message = "First name is mandatory")
    @Length(max = PatientConstraints.FIRST_NAME_MAX_SIZE, message = "The maximum length for firstName is 125 characters")
    private String firstName;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Please enter a valid birth date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotEmpty(message = "Sex is mandatory")
    @Length(max = PatientConstraints.SEX_MAX_SIZE, message = "The maximum length for sex is 1 characters")
    @Pattern(regexp = "^[M|F]{1}$", message = "Please enter character M or F")
    private String sex;

    @Length(max = PatientConstraints.ADDRESS_MAX_SIZE, message = "The maximum length for address is 150 characters")
    private String address;

    @Length(max = PatientConstraints.PHONE_MAX_SIZE, message = "Please enter a valid phone number")
    private String phoneNumber;

    public PatientDTO(String lastName, String firstName, LocalDate birthDate, String sex, String address,
                      String phoneNumber) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
