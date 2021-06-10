package com.mediscreen.patient.integration;

import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.exception.DataAlreadyRegisteredException;
import com.mediscreen.patient.exception.ResourceNotFoundException;
import com.mediscreen.patient.service.PatientService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PatientServiceIT {

    @Autowired
    private PatientService patientService;

    @Test
    @Tag("AddPatient")
    @DisplayName("If Patient is not registered, when addPatient, then return Patient saved")
    public void givenAnUnRegisteredPatient_whenAddPatient_thenPatientSavedShouldBeReturned() {
        PatientDTO patientDTO = new PatientDTO("Durant", "Simon",
                LocalDate.of(1991,8,1), "M", "5 Warren Street", "397-866-1344");

        PatientDTO patientSaved = patientService.addPatient(patientDTO);

        assertNotNull(patientSaved);
        assertThat(patientSaved.getLastName()).isEqualTo("Durant");
    }

    @Test(expected = DataAlreadyRegisteredException.class)
    @Tag("AddPatient")
    @DisplayName("If Patient is already registered, when addPatient, then throw DataAlreadyRegisteredException")
    public void givenARegisteredPatient_whenAddPatient_thenDataAlreadyRegisteredExceptionIsThrown() {
        PatientDTO patient = new PatientDTO("Ferguson", "Lucas",
                LocalDate.of(1968,06,22), "M", "2 Warren Street", "387-866-1399");

        patientService.addPatient(patient);
    }

    @Test
    @Tag("UpdatePatient")
    @DisplayName("Given an Patient to update, when updatePatient, then return Patient updated")
    public void givenAnPatientToUpdate_whenUpdatePatient_thenPatientUpdatedShouldBeReturned() {
        PatientDTO patientToUpdate = new PatientDTO("Ferguson", "Lucas",
                LocalDate.of(1968,06,22), "M", "11 Warren Street", "387-866-1399");

        PatientDTO patientUpdated = patientService.updatePatient(1, patientToUpdate);

        assertNotNull(patientUpdated);
        assertThat(patientUpdated.getAddress()).isEqualTo("11 Warren Street");
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdatePatient")
    @DisplayName("If Patient id cant be found, when updatePatient, then throw ResourceNotFoundException")
    public void givenUnFoundPatientId_whenUpdatePatient_thenResourceNotFoundExceptionIsThrown() {
        PatientDTO patientToUpdate = new PatientDTO("Durant", "Simon",
                LocalDate.of(1991,8,1), "M", "5 Warren Street", "397-866-1344");

        patientService.updatePatient(13, patientToUpdate);
    }

    @Test
    @Tag("DeletePatient")
    @DisplayName("Given an Patient to delete, when deletePatient, then Patient should be delete successfully")
    public void givenAnPatientId_whenDeletePatient_thenPatientShouldBeDeleteSuccessfully() {
        patientService.deletePatient(2);

        assertThrows(ResourceNotFoundException.class, () -> { patientService.getPatientById(2);
        });
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeletePatient")
    @DisplayName("If Patient id cant be found, when deletePatient, then throw ResourceNotFoundException")
    public void givenUnFoundPatientId_whenDeletePatient_thenResourceNotFoundExceptionIsThrown() {
        patientService.deletePatient(16);
    }

    @Test
    @Tag("GetPatientById")
    @DisplayName("Given an Patient id, when getPatientById, then expected Patient should be returned")
    public void givenAnPatientId_whenGetPatientById_thenExpectedPatientShouldBeReturned() {
        PatientDTO patient = patientService.getPatientById(1);

        assertNotNull(patient);
        assertThat(patient.getLastName()).isEqualTo("Ferguson");
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetPatientById")
    @DisplayName("If Patient id cant be found, when getPatientById, then throw ResourceNotFoundException")
    public void givenUnFoundPatientId_whenGetPatientById_thenResourceNotFoundExceptionIsThrown() {
        patientService.getPatientById(16);
    }

    @Test
    @Tag("GetAllPatient")
    @DisplayName("Given a null keyword, When getAllPatient, then Patient list should be returned")
    public void givenNullKeyword_whenGetAllPatient_thenPatientListShouldBeReturned() {
        List<PatientDTO> patients = patientService.getAllPatient(null);

        assertNotNull(patients);
        assertThat(patients.size()).isEqualTo(10);
    }

    @Test
    @Tag("GetAllPatient")
    @DisplayName("Given a null keyword, When getAllPatient, then only patients that match the keyword should be returned")
    public void givenAKeyword_whenGetAllPatient_thenPatientsThatMatchTheKeywordShouldBeReturned() {
        List<PatientDTO> patients = patientService.getAllPatient("Ferguson");

        assertNotNull(patients);
        assertThat(patients.size()).isEqualTo(1);
        assertThat(patients.get(0).getFirstName()).isEqualTo("Lucas");
    }
}
