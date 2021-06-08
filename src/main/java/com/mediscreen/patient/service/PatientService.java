package com.mediscreen.patient.service;

import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.exception.DataAlreadyRegisteredException;
import com.mediscreen.patient.exception.ResourceNotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.util.DTOConverter;
import com.mediscreen.patient.util.ModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    private static final Logger LOGGER = LogManager.getLogger(PatientService.class);

    private final PatientRepository patientRepository;

    private final DTOConverter dtoConverter;

    private final ModelConverter modelConverter;

    @Autowired
    public PatientService(final PatientRepository patientRepository, final DTOConverter dtoConverter,
                          final ModelConverter modelConverter) {
        this.patientRepository = patientRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    public PatientDTO addPatient(final PatientDTO patientDTO) {
        LOGGER.debug("Inside PatientService.addPatient");

        Patient patientFound = patientRepository.findByLastNameAndFirstNameAndBirthDate(patientDTO.getLastName(),
                patientDTO.getFirstName(), patientDTO.getBirthDate());

        if (patientFound != null) {
            throw new DataAlreadyRegisteredException("This patient may be registered already");
        }
        Patient patientSaved = patientRepository.save(modelConverter.toPatient(patientDTO));

        return dtoConverter.toPatientDTO(patientSaved);
    }

    public PatientDTO updatePatient(final int patientId, final PatientDTO patientDTO) {
        LOGGER.debug("Inside PatientService.updatePatient");

        patientRepository.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("No patient registered with this id"));

        Patient patientToUpdate = modelConverter.toPatient(patientDTO);
        patientToUpdate.setId(patientId);
        Patient patientUpdated = patientRepository.save(patientToUpdate);

        return dtoConverter.toPatientDTO(patientUpdated);
    }

    public PatientDTO getPatientById(final int patientId) {
        LOGGER.debug("Inside PatientService.getPatientById");

        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("No patient registered with this id"));

        return dtoConverter.toPatientDTO(patient);
    }

    public List<PatientDTO> getAllPatient(final String keyword) {
        LOGGER.debug("Inside PatientService.getAllPatient");

        if (keyword != null) {
            List<PatientDTO> searchPatient = patientRepository.findByKeyword(keyword).stream()
                    .map(patient -> dtoConverter.toPatientDTO(patient))
                    .collect(Collectors.toList());

            return searchPatient;
        }

        List<PatientDTO> allPatient = patientRepository.findAll().stream()
                .map(patient -> dtoConverter.toPatientDTO(patient))
                .collect(Collectors.toList());

        return allPatient;
    }

    public void deletePatient(final int patientId) {
        LOGGER.debug("Inside PatientService.deletePatient");

        patientRepository.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("No patient registered with this id"));

        patientRepository.deleteById(patientId);
    }
}
