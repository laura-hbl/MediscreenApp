package com.mediscreen.patient.util;

import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.model.Patient;
import org.springframework.stereotype.Component;

@Component
public class DTOConverter {

    public PatientDTO toPatientDTO(final Patient patient) {

        return new PatientDTO(patient.getId(), patient.getLastName(), patient.getFirstName(), patient.getBirthDate(),
                patient.getSex(), patient.getAddress(), patient.getPhoneNumber());
    }
}
