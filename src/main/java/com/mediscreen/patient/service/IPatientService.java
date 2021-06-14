package com.mediscreen.patient.service;

import com.mediscreen.patient.dto.PatientDTO;

import java.util.List;

public interface IPatientService {

    PatientDTO addPatient(final PatientDTO patientDTO);

    PatientDTO updatePatient(final int patientId, final PatientDTO patientDTO);

    PatientDTO getPatientById(final int patientId);

    void deletePatient(final int patientId);

    List<PatientDTO> getAllPatient(final String keyword);
}
