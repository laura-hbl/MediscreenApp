package com.mediscreen.patient.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patient.controller.PatientController;
import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.service.IPatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @MockBean
    private IPatientService patientService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;

    private PatientDTO patientDTO;

    // URL
    private final static String PATIENT_GET_URL = "/patient/get/";
    private final static String PATIENT_DELETE_URL = "/patient/delete/";
    private final static String PATIENT_LIST_URL = "/patient/list/";
    private final static String PATIENT_UPDATE_URL = "/patient/update/";
    private final static String PATIENT_ADD_URL = "/patient/add";

    @BeforeEach
    public void setup() {
        patientDTO = new PatientDTO(1, "Durant", "Simon",
                LocalDate.parse("1967-09-16"), "M", "5 Warren Street", "397-866-1344");

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("addPatient")
    @DisplayName("Given a Patient to add, when addPatient, then return Ok status")
    public void givenAValidPatientToAdd_whenAddPatient_thenReturnOkStatus() throws Exception {
        when(patientService.addPatient(any(PatientDTO.class))).thenReturn(patientDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(PATIENT_ADD_URL)
                .content(mapper.writeValueAsString(patientDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    @Test
    @Tag("addPatient")
    @DisplayName("Give empty LastName field, when addPatient request, then return error message")
    void givenAnEmptyPatientLastNameField_whenAddPatient_thenReturnErrorMessage() throws Exception {
        PatientDTO patientDTO = new PatientDTO("", "Simon",
                LocalDate.parse("1967-09-16"), "M", "5 Warren Street", "397-866-1344");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PATIENT_ADD_URL)
                        .content(mapper.writeValueAsString(patientDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Last name is mandatory");
        verify(patientService, times(0)).addPatient(any(PatientDTO.class));
    }

    @Test
    @Tag("addPatient")
    @DisplayName("Given invalid sex value, when addPatient request, then return error message")
    void givenAnInvalidSexValue_whenAddPatient_thenReturnErrorMessage() throws Exception {
        PatientDTO patientDTO = new PatientDTO("Durant", "Simon",
                LocalDate.parse("1967-09-16"), "P", "5 Warren Street", "397-866-1344");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PATIENT_ADD_URL)
                .content(mapper.writeValueAsString(patientDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Please enter character M or F");
        verify(patientService, times(0)).addPatient(any(PatientDTO.class));
    }

    @Test
    @Tag("updatePatient")
    @DisplayName("Given a Patient to update, when updatePatient, then return Ok status")
    public void givenAPatientToUpdate_whenUpdatePatient_thenReturnOkStatus() throws Exception {
        when(patientService.updatePatient(1, patientDTO)).thenReturn(patientDTO);

        mockMvc.perform(MockMvcRequestBuilders.post(PATIENT_UPDATE_URL + 1)
                .content(mapper.writeValueAsString(patientDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    @Tag("UpdatePatient")
    @DisplayName("Given an invalid birthDate, when updatePatient request, then return error message and Patient update form")
    void givenAnInvalidBirthDate_whenUpdatePatient_thenReturnErrorMessageAndPatientUpdateForm() throws Exception {
        PatientDTO patientDTO = new PatientDTO("Durant", "Simon",
                LocalDate.parse("9999-09-16"), "P", "5 Warren Street", "397-866-1344");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(PATIENT_UPDATE_URL + 1)
                .content(mapper.writeValueAsString(patientDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Please enter a valid birth date");
        verify(patientService, times(0)).updatePatient(anyInt(), any(PatientDTO.class));
    }

    @Test
    @Tag("getPatientById")
    @DisplayName("Given a Patient to retrieves, when getPatientById, then return Ok status")
    public void givenAPatientToRetrieves_whenGetPatientById_thenReturnOkStatus() throws Exception {
        when(patientService.getPatientById(1)).thenReturn(patientDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(PATIENT_GET_URL + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Durant");
        verify(patientService).getPatientById(1);
    }

    @Test
    @Tag("deletePatient")
    @DisplayName("Given a Patient to delete, when deletePatient, then return Ok status")
    public void givenAPatientToDelete_whenDeletePatient_thenReturnOkStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATIENT_DELETE_URL + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(patientService).deletePatient(1);
    }

    @Test
    @Tag("getAllPatient")
    @DisplayName("Given a null keyword and a patient list, when getAllPatient, then return Ok status")
    public void givenANullKeywordAndPatientList_whenGetAllPatient_thenReturnOkStatus() throws Exception {
        PatientDTO patient2 = new PatientDTO(2, "Ferguson", "Lucas",
                LocalDate.of(1968, 06, 22), "M", "11 Warren Street", "387-866-1399");

        when(patientService.getAllPatient(null)).thenReturn(Arrays.asList(patientDTO, patient2));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(PATIENT_LIST_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        verify(patientService).getAllPatient(null);
        assertThat(content).contains("Durant", "Ferguson");
    }

    @Test
    @Tag("getAllPatient")
    @DisplayName("Given a keyword and a patient, when getAllPatient, then return Ok status")
    public void givenAKeywordAndAPatient_whenGetAllPatient_thenReturnOkStatus() throws Exception {
        when(patientService.getAllPatient("Durant")).thenReturn(Arrays.asList(patientDTO));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(PATIENT_LIST_URL + "?keyword=Durant")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        verify(patientService).getAllPatient("Durant");
        assertThat(content).contains("Durant");
    }
}
