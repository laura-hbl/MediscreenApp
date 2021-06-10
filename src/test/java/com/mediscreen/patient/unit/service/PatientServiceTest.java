package com.mediscreen.patient.unit.service;

import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.exception.DataAlreadyRegisteredException;
import com.mediscreen.patient.exception.ResourceNotFoundException;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.repository.PatientRepository;
import com.mediscreen.patient.service.PatientService;
import com.mediscreen.patient.util.DTOConverter;
import com.mediscreen.patient.util.ModelConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private ModelConverter modelConverter;

    private static Patient patient1;

    private static Patient patient2;

    private static PatientDTO patient1DTO;

    private static PatientDTO patient2DTO;

    private static List<PatientDTO> patientListDTO;

    @Before
    public void setUp() {
        patient1DTO = new PatientDTO(1, "Durant", "Simon",
                LocalDate.of(1991, 8, 1), "M", "5 Warren Street", "397-866-1344");
        patient2DTO = new PatientDTO(2, "Ferguson", "Lucas",
                LocalDate.of(1968, 06, 22), "M", "11 Warren Street", "387-866-1399");
        patient1 = new Patient(1, "Durant", "Simon",
                LocalDate.of(1991, 8, 1), "M", "5 Warren Street", "397-866-1344");
        patient2 = new Patient(2, "Ferguson", "Lucas",
                LocalDate.of(1968, 06, 22), "M", "11 Warren Street", "387-866-1399");
        patientListDTO = Arrays.asList(patient1DTO, patient2DTO);
    }


    @Test
    @Tag("AddPatient")
    @DisplayName("If Patient is not registered, when addPatient, then Patient should be saved correctly")
    public void givenAnUnRegisteredPatient_whenAddPatient_thenPatientShouldBeSavedCorrectly() {
        Patient patientToAdd = new Patient("Durant", "Simon",
                LocalDate.of(1991, 8, 1), "M", "5 Warren Street", "397-866-1344");
        PatientDTO patientToAddDTO = new PatientDTO("Durant", "Simon",
                LocalDate.of(1991, 8, 1), "M", "5 Warren Street", "397-866-1344");
        when(patientRepository.findByLastNameAndFirstNameAndBirthDate(anyString(), anyString(), any(LocalDate.class))).thenReturn(null);
        when(modelConverter.toPatient(any(PatientDTO.class))).thenReturn(patientToAdd);
        when(patientRepository.save(any(Patient.class))).thenReturn(patient1);
        when(dtoConverter.toPatientDTO(any(Patient.class))).thenReturn(patient1DTO);

        PatientDTO patientSaved = patientService.addPatient(patientToAddDTO);

        assertThat(patientSaved).isEqualToComparingFieldByField(patient1DTO);
        InOrder inOrder = inOrder(patientRepository, modelConverter, dtoConverter);
        inOrder.verify(patientRepository).findByLastNameAndFirstNameAndBirthDate(anyString(), anyString(), any(LocalDate.class));
        inOrder.verify(modelConverter).toPatient(any(PatientDTO.class));
        inOrder.verify(patientRepository).save(any(Patient.class));
        inOrder.verify(dtoConverter).toPatientDTO(any(Patient.class));
    }

    @Test(expected = DataAlreadyRegisteredException.class)
    @Tag("AddPatient")
    @DisplayName("If Patient is already registered, when AddPatient, then throw DataAlreadyRegisteredException")
    public void givenARegisteredPatient_whenAddPatient_thenDataAlreadyRegisteredExceptionIsThrown() {
        when(patientRepository.findByLastNameAndFirstNameAndBirthDate(anyString(), anyString(), any(LocalDate.class))).thenReturn(patient1);

        patientService.addPatient(patient1DTO);
    }

    @Test
    @Tag("UpdatePatient")
    @DisplayName("Given a registered Patient, when updatePatient, then Patient should be updated correctly")
    public void givenARegisteredPatient_whenUpdatePatient_thenPatientShouldBeUpdateCorrectly() {
        Patient patientUpdated = new Patient(1, "Durant", "Simon",
                LocalDate.of(1991, 8, 1), "M", "5 Warren Street", "777-866-1399");
        PatientDTO patientUpdatedDTO = new PatientDTO(1, "Durant", "Simon",
                LocalDate.of(1991, 8, 1), "M", "5 Warren Street", "777-866-1399");
        when(patientRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(patient1));
        when(modelConverter.toPatient(any(PatientDTO.class))).thenReturn(new Patient("Durant", "Simon",
                LocalDate.of(1991, 8, 1), "M", "5 Warren Street", "777-866-1399"));
        when(patientRepository.save(any(Patient.class))).thenReturn(patientUpdated);
        when(dtoConverter.toPatientDTO(any(Patient.class))).thenReturn(patientUpdatedDTO);

        PatientDTO result = patientService.updatePatient(1, new PatientDTO("Durant", "Simon",
                LocalDate.of(1991, 8, 1), "M", "5 Warren Street", "777-866-1399"));

        assertThat(result).isEqualTo(patientUpdatedDTO);
        InOrder inOrder = inOrder(patientRepository, modelConverter, dtoConverter);
        inOrder.verify(patientRepository).findById(anyInt());
        inOrder.verify(modelConverter).toPatient(any(PatientDTO.class));
        inOrder.verify(patientRepository).save(any(Patient.class));
        inOrder.verify(dtoConverter).toPatientDTO(any(Patient.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdatePatient")
    @DisplayName("If Patient cant be found, when updatePatient, then throw ResourceNotFoundException")
    public void givenUnFoundPatient_whenUpdatePatient_thenResourceNotFoundExceptionIsThrown() {
        when(patientRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        patientService.updatePatient(1, patient1DTO);
    }

    @Test
    @Tag("DeletePatient")
    @DisplayName("Given Patient Id, when deletePatient, then delete process should be done in correct order")
    public void givenAPatientId_whenDeletePatient_thenDeletingShouldBeDoneInCorrectOrder() {
        when(patientRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(patient1));

        patientService.deletePatient(anyInt());

        InOrder inOrder = inOrder(patientRepository);
        inOrder.verify(patientRepository).findById(anyInt());
        inOrder.verify(patientRepository).deleteById(anyInt());
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeletePatient")
    @DisplayName("If Patient can't be found, when deletePatient, then throw ResourceNotFoundException")
    public void givenUnFoundPatient_whenDeletePatient_thenResourceNotFoundExceptionIsThrown() {
        when(patientRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        patientService.deletePatient(anyInt());
    }

    @Test
    @Tag("GetPatientById")
    @DisplayName("Given an Patient id, when getPatientById, then expected Patient should be returned correctly")
    public void givenAnPatientId_whenGetPatientById_thenExpectedPatientShouldBeReturnCorrectly() {
        when(patientRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(patient1));
        when(dtoConverter.toPatientDTO(any(Patient.class))).thenReturn(patient1DTO);

        PatientDTO patientFound = patientService.getPatientById(1);

        assertThat(patientFound).isEqualToComparingFieldByField(patient1DTO);

        InOrder inOrder = inOrder(patientRepository, dtoConverter);
        inOrder.verify(patientRepository).findById(anyInt());
        inOrder.verify(dtoConverter).toPatientDTO(any(Patient.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetPatientById")
    @DisplayName("If Patient can't be found, when getPatientById, then throw ResourceNotFoundException")
    public void givenUnFoundPatient_whenGetPatientById_thenResourceNotFoundExceptionIsThrown() {
        when(patientRepository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        patientService.getPatientById(1);
    }

    @Test
    @Tag("GetAllPatient")
    @DisplayName("Given a null keyword, when getAllPatient, then result should match expected Patient list")
    public void givenANullKeyword_whenGetAllPatient_thenReturnAllPatientList() {
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));
        when(dtoConverter.toPatientDTO(patient1)).thenReturn(patient1DTO);
        when(dtoConverter.toPatientDTO(patient2)).thenReturn(patient2DTO);

        List<PatientDTO> result = patientService.getAllPatient(null);

        assertThat(result).isEqualTo(patientListDTO);
        InOrder inOrder = inOrder(patientRepository, dtoConverter);
        inOrder.verify(patientRepository).findAll();
        inOrder.verify(dtoConverter).toPatientDTO(patient1);
        inOrder.verify(dtoConverter).toPatientDTO(patient2);
    }

    @Test
    @Tag("GetAllPatient")
    @DisplayName("Given a keyword, when getAllPatient, then result should match expected Patient with matching keyword")
    public void givenAKeyword_whenGetAllPatient_thenReturnPatientThatMatchKeyword() {
        when(patientRepository.findByKeyword(anyString())).thenReturn(Arrays.asList(patient1));
        when(dtoConverter.toPatientDTO(patient1)).thenReturn(patient1DTO);

        List<PatientDTO> result = patientService.getAllPatient(anyString());

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(patient1DTO);
        InOrder inOrder = inOrder(patientRepository, dtoConverter);
        inOrder.verify(patientRepository).findByKeyword(anyString());
        inOrder.verify(dtoConverter).toPatientDTO(patient1);
    }
}