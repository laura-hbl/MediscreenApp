package com.mediscreen.ui.unit;

import com.mediscreen.ui.controller.AssessmentController;
import com.mediscreen.ui.dto.AssessmentDTO;
import com.mediscreen.ui.dto.PatientDTO;
import com.mediscreen.ui.proxy.MicroserviceAssessProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AssessmentController.class)
public class AssessmentControllerTest {

    @MockBean
    private MicroserviceAssessProxy assessProxy;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("getPatientAssessment")
    @DisplayName("Given a patient id, when getPatientAssessment request, then display Patient assessment page")
    public void whenShowUpdateFormRequest_thenDisplayPatientUpdateForm() throws Exception {
        AssessmentDTO assessmentDTO = new AssessmentDTO(new PatientDTO(1, "Durant", "Simon",
                LocalDate.of(1991,8,1), "M", "5 Warren Street",
                "397-866-1344"), "BORDELINE");

        when(assessProxy.getPatientAssessment(1)).thenReturn(assessmentDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/assess/1"))
                .andExpect(model().attributeExists("patientId"))
                .andExpect(model().attributeExists("assessmentDTO"))
                .andExpect(view().name("assessment/assess"))
                .andExpect(status().isOk());

        verify(assessProxy).getPatientAssessment(1);
    }
}