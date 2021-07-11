package com.mediscreen.patient.constant;


import lombok.NoArgsConstructor;

/**
 * Contains the different Patient validator constraints.
 *
 * @author Laura Habdul
 */
@NoArgsConstructor
public class PatientConstraints {

    /**
     * Maximum number of characters allowed for the first name.
     */
    public static final int FIRST_NAME_MAX_SIZE = 125;

    /**
     * Maximum number of characters allowed for the last name.
     */
    public static final int LAST_NAME_MAX_SIZE = 125;

    /**
     * Maximum number of characters allowed for the sex.
     */
    public static final int SEX_MAX_SIZE = 1;

    /**
     * Maximum number of characters allowed for the address.
     */
    public static final int ADDRESS_MAX_SIZE = 150;

    /**
     * Maximum number of characters allowed for the phone number.
     */
    public static final int PHONE_MAX_SIZE = 20;
}

