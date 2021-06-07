package com.mediscreen.patient.util;

import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.model.Patient;
import org.springframework.stereotype.Component;

@Component
public class ModelConverter {

    public Patient toPatient(final PatientDTO patientDTO) {

        return new Patient(patientDTO.getLastName(), patientDTO.getFirstName(), patientDTO.getBirthDate(),
                patientDTO.getSex(), patientDTO.getAddress(), patientDTO.getPhoneNumber());
    }
}
