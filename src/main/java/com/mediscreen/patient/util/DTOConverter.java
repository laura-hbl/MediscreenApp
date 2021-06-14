package com.mediscreen.patient.util;

import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.model.Patient;
import org.springframework.stereotype.Component;

/**
 * Allows the conversion of Model class to DTO class.
 *
 * @author Laura Habdul
 */
@Component
public class DTOConverter {

    /**
     * Converts Patient to PatientDTO.
     *
     * @param patient Patient object to convert
     * @return The PatientDTO object
     */
    public PatientDTO toPatientDTO(final Patient patient) {

        return new PatientDTO(patient.getId(), patient.getLastName(), patient.getFirstName(), patient.getBirthDate(),
                patient.getSex(), patient.getAddress(), patient.getPhoneNumber());
    }
}
