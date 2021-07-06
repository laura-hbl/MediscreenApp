package com.mediscreen.patientAssessment.service;

import com.mediscreen.patientAssessment.dto.AssessmentDTO;
import com.mediscreen.patientAssessment.dto.NoteDTO;
import com.mediscreen.patientAssessment.dto.PatientDTO;
import com.mediscreen.patientAssessment.proxy.MicroserviceNoteProxy;
import com.mediscreen.patientAssessment.proxy.MicroservicePatientProxy;
import com.mediscreen.patientAssessment.util.AgeCalculator;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssessmentServiceTest {

    @InjectMocks
    private AssessmentService assessmentService;

    @Mock
    private MicroserviceNoteProxy noteProxy;

    @Mock
    private MicroservicePatientProxy patientProxy;

    @Mock
    private AgeCalculator ageCalculator;

    @Test
    @Tag("getPatientAssessment")
    @DisplayName("Given a patient with expected risk level None, when getPatientAssessment, then expected Assessment should be return")
    public void givenAPatientWithExpectedNoneRiskLevel_whenGetPatientAssessment_thenExpectedAssessmentShouldBeReturn() {
        PatientDTO patientDTO = new PatientDTO(1, "Durant", "Simon",
                LocalDate.of(1991,8,1), "M", "5 Warren Street", "397-866-1344");
        NoteDTO note1DTO = new NoteDTO("1", 1, LocalDate.of(2020,06,22), "note");
        NoteDTO note2DTO = new NoteDTO("2", 1, LocalDate.of(2020,06,29), "note");
        when(noteProxy.getAllNote(1)).thenReturn(Arrays.asList(note1DTO, note2DTO));
        when(patientProxy.getPatientById(1)).thenReturn(patientDTO);
        when(ageCalculator.getAge(patientDTO.getBirthDate())).thenReturn(29);

        AssessmentDTO assessmentDTO = assessmentService.getPatientAssessment(1);

        assertThat(assessmentDTO.getDiabetesRiskLevel()).isEqualTo("None");

        InOrder inOrder = inOrder(noteProxy, patientProxy, ageCalculator);
        inOrder.verify(noteProxy).getAllNote(1);
        inOrder.verify(patientProxy).getPatientById(1);
        inOrder.verify(ageCalculator).getAge(patientDTO.getBirthDate());
    }

    @Test
    @Tag("getPatientAssessment")
    @DisplayName("Given a patient with expected risk level Borderline, when getPatientAssessment, then expected Assessment should be returned correctly")
    public void givenAPatientWithExpectedBorderlineRiskLevel_thenExpectedAssessmentShouldBeReturnCorrectly() {
        PatientDTO patientDTO = new PatientDTO(1, "Durant", "Simon",
                LocalDate.of(1989,8,1), "M", "5 Warren Street", "397-866-1344");
        NoteDTO note1DTO = new NoteDTO("1", 1, LocalDate.of(2020,06,22), "Reaction");
        NoteDTO note2DTO = new NoteDTO("2", 1, LocalDate.of(2020,06,29), "Cholesterol");
        when(noteProxy.getAllNote(1)).thenReturn(Arrays.asList(note1DTO, note2DTO));
        when(patientProxy.getPatientById(1)).thenReturn(patientDTO);
        when(ageCalculator.getAge(patientDTO.getBirthDate())).thenReturn(31);

        AssessmentDTO assessmentDTO = assessmentService.getPatientAssessment(1);

        assertThat(assessmentDTO.getDiabetesRiskLevel()).isEqualTo("Borderline");

        InOrder inOrder = inOrder(noteProxy, patientProxy, ageCalculator);
        inOrder.verify(noteProxy).getAllNote(1);
        inOrder.verify(patientProxy).getPatientById(1);
        inOrder.verify(ageCalculator).getAge(patientDTO.getBirthDate());
    }

    @Test
    @Tag("getPatientAssessment")
    @DisplayName("Given a patient with expected risk level In danger, when getPatientAssessment, then expected Assessment should be returned correctly")
    public void givenAPatientWithExpectedInDangerRiskLevel_thenExpectedAssessmentShouldBeReturnCorrectly() {
        PatientDTO patientDTO = new PatientDTO(1, "Laura", "Simon",
                LocalDate.of(1991,8,1), "F", "5 Warren Street", "397-866-1344");
        NoteDTO note1DTO = new NoteDTO("1", 1, LocalDate.of(2020,06,22), "Reaction");
        NoteDTO note2DTO = new NoteDTO("2", 1, LocalDate.of(2020,06,29), "Cholesterol");
        NoteDTO note3DTO = new NoteDTO("3", 1, LocalDate.of(2020,06,22), "Smoker");
        NoteDTO note4DTO = new NoteDTO("4", 1, LocalDate.of(2020,06,29), "Abnormal");
        when(noteProxy.getAllNote(1)).thenReturn(Arrays.asList(note1DTO, note2DTO, note3DTO, note4DTO));
        when(patientProxy.getPatientById(1)).thenReturn(patientDTO);
        when(ageCalculator.getAge(patientDTO.getBirthDate())).thenReturn(29);

        AssessmentDTO assessmentDTO = assessmentService.getPatientAssessment(1);

        assertThat(assessmentDTO.getDiabetesRiskLevel()).isEqualTo("In danger");

        InOrder inOrder = inOrder(noteProxy, patientProxy, ageCalculator);
        inOrder.verify(noteProxy).getAllNote(1);
        inOrder.verify(patientProxy).getPatientById(1);
        inOrder.verify(ageCalculator).getAge(patientDTO.getBirthDate());
    }

    @Test
    @Tag("getPatientAssessment")
    @DisplayName("Given a patient with expected risk level Early onset, when getPatientAssessment, then expected Assessment should be returned correctly")
    public void givenAPatientWithExpectedEarlyOnsetRiskLevel_thenExpectedAssessmentShouldBeReturnCorrectly() {
        PatientDTO patientDTO = new PatientDTO(1, "Simon", "Simon",
                LocalDate.of(1991,8,1), "M", "5 Warren Street", "397-866-1344");
        NoteDTO note1DTO = new NoteDTO("1", 1, LocalDate.of(2020,06,22), "Reaction");
        NoteDTO note2DTO = new NoteDTO("2", 1, LocalDate.of(2020,06,29), "Cholesterol");
        NoteDTO note3DTO = new NoteDTO("3", 1, LocalDate.of(2020,06,22), "Smoker");
        NoteDTO note4DTO = new NoteDTO("4", 1, LocalDate.of(2020,06,29), "Abnormal");
        NoteDTO note5DTO = new NoteDTO("5", 1, LocalDate.of(2020,06,22), "Dizziness");
        when(noteProxy.getAllNote(1)).thenReturn(Arrays.asList(note1DTO, note2DTO, note3DTO, note4DTO, note5DTO));
        when(patientProxy.getPatientById(1)).thenReturn(patientDTO);
        when(ageCalculator.getAge(patientDTO.getBirthDate())).thenReturn(29);

        AssessmentDTO assessmentDTO = assessmentService.getPatientAssessment(1);

        assertThat(assessmentDTO.getDiabetesRiskLevel()).isEqualTo("Early onset");

        InOrder inOrder = inOrder(noteProxy, patientProxy, ageCalculator);
        inOrder.verify(noteProxy).getAllNote(1);
        inOrder.verify(patientProxy).getPatientById(1);
        inOrder.verify(ageCalculator).getAge(patientDTO.getBirthDate());
    }
}
