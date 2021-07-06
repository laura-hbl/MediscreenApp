package com.mediscreen.patientAssessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patientAssessment.dto.AssessmentDTO;
import com.mediscreen.patientAssessment.dto.PatientDTO;
import com.mediscreen.patientAssessment.service.IAssessmentService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AssessmentController.class)
public class AssessmentControllerTest {

    @MockBean
    private IAssessmentService assessmentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;

    // URL
    private final static String ASSESS_GET_URL = "/assess/";

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("getPatientAssessment")
    @DisplayName("Given a patient id, when getPatientAssessment request, then return OK status")
    public void givenAPatientId_whenGetPatientAssessment_thenReturnOKStatus() throws Exception {
        AssessmentDTO assessmentDTO = new AssessmentDTO(new PatientDTO(1, "Durant", "Simon",
                LocalDate.of(1991,8,1), "M", "5 Warren Street",
                "397-866-1344"), "BORDELINE");
        when(assessmentService.getPatientAssessment(1)).thenReturn(assessmentDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(ASSESS_GET_URL + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("BORDELINE");
        verify(assessmentService).getPatientAssessment(1);
    }
}
