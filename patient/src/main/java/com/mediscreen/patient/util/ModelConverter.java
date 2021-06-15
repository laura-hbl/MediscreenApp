package com.mediscreen.patient.util;

import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.model.Patient;
import org.springframework.stereotype.Component;

/**
 * Allows the conversion of DTO class to Model class.
 *
 * @author Laura Habdul
 */
@Component
public class ModelConverter {

    /**
     * Converts PatientDTO to Patient.
     *
     * @param patientDTO PatientDTO object to convert
     * @return The Patient object
     */
    public Patient toPatient(final PatientDTO patientDTO) {

        return new Patient(patientDTO.getLastName(), patientDTO.getFirstName(), patientDTO.getBirthDate(),
                patientDTO.getSex(), patientDTO.getAddress(), patientDTO.getPhoneNumber());
    }
}
