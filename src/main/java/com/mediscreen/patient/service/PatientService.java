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

/**
 * Contains methods that allow interaction between Patient business logic and Patient repository.
 *
 * @author Laura Habdul
 */
@Service
public class PatientService implements IPatientService {

    /**
     * PatientService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(PatientService.class);

    /**
     * PatientRepository instance.
     */
    private final PatientRepository patientRepository;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * ModelConverter instance.
     */
    private final ModelConverter modelConverter;

    /**
     * Constructor of class PatientService.
     * Initialize patientRepository, dtoConverter and modelConverter.
     *
     * @param patientRepository UserRepository instance
     * @param dtoConverter      DTOConverter instance
     * @param modelConverter    ModelConverter instance
     */
    @Autowired
    public PatientService(final PatientRepository patientRepository, final DTOConverter dtoConverter,
                          final ModelConverter modelConverter) {
        this.patientRepository = patientRepository;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    /**
     * Calls PatientRepository's findByLastNameAndFirstNameAndBirthDate method to retrieves the patient with the given
     * firstName, lastName and birthDate and checks if patient is not already registered, if so
     * DataAlreadyRegisteredException is thrown. Else, converts PatientDTO object to model object and save it by calling
     * PatientRepository's save method and then converts the saved Patient model object to PatientDTO object.
     *
     * @param patientDTO the patient to be registered
     * @return The patient saved converted to a PatientDTO object
     */
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

    /**
     * Checks if the given patient to update is registered by calling UserRepository's findById method, if so converts
     * the PatientDTO object to an Patient object, set id, then saves it to database by calling PatientRepository's save
     * method and converted to a PatientDTO object. Else, ResourceNotFoundException is thrown.
     *
     * @param patientId  id of the patient to be updated
     * @param patientDTO the patient to be updated
     * @return The patient updated converted to a PatientDTO object
     */
    public PatientDTO updatePatient(final int patientId, final PatientDTO patientDTO) {
        LOGGER.debug("Inside PatientService.updatePatient");

        patientRepository.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("No patient registered with this id"));

        Patient patientToUpdate = modelConverter.toPatient(patientDTO);
        patientToUpdate.setId(patientId);
        Patient patientUpdated = patientRepository.save(patientToUpdate);

        return dtoConverter.toPatientDTO(patientUpdated);
    }

    /**
     * Calls PatientRepository's findById method to retrieves the patient with the given id and checks if patient exists
     * in database, if so converts the found Patient toPatientDTO object. Else, ResourceNotFoundException is thrown.
     *
     * @param patientId id of the patient to be found
     * @return The patient found converted to an PatientDTO object
     */
    public PatientDTO getPatientById(final int patientId) {
        LOGGER.debug("Inside PatientService.getPatientById");

        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("No patient registered with this id"));

        return dtoConverter.toPatientDTO(patient);
    }

    /**
     * Checks if the keyword is null, if not, retrieves the list of patients whose first or last name matches
     * the entered keyword, then, converts each patient from the list to a PatientDTO object and adds it to a List.
     * Else, retrieves all patients by calling PatientRepository's findAll() method, each patient from the list is
     * converted to a PatientDTO object and adds it to a List.
     *
     * @return All the patient list or the list of patients whose first or last name matches the entered keyword.
     */
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

    /**
     * Checks if the given patient to delete is registered by calling PatientRepository's findById method, if so patient
     * found is deleted by calling PatientRepository's delete method. Else, ResourceNotFoundException is thrown.
     *
     * @param patientId id of the patient to be deleted
     */
    public void deletePatient(final int patientId) {
        LOGGER.debug("Inside PatientService.deletePatient");

        patientRepository.findById(patientId).orElseThrow(() ->
                new ResourceNotFoundException("No patient registered with this id"));

        patientRepository.deleteById(patientId);
    }
}
