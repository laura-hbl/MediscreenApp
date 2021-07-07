package com.mediscreen.ui.unit;

import com.mediscreen.ui.controller.PatientController;
import com.mediscreen.ui.dto.PatientDTO;
import com.mediscreen.ui.proxy.MicroservicePatientProxy;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @MockBean
    private MicroservicePatientProxy patientProxy;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private PatientDTO patient1DTO;

    private PatientDTO patient2DTO;

    @BeforeEach
    public void setUp() {
        patient1DTO = new PatientDTO("Durant", "Simon",
                LocalDate.of(1991,8,1), "M", "5 Warren Street", "397-866-1344");
        patient2DTO = new PatientDTO("Ferguson", "Lucas",
                LocalDate.of(1968,06,22), "M", "11 Warren Street", "387-866-1399");

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("ShowPatientList")
    @DisplayName("Given a null Keyword, when showPatientList request, then display Patient list")
    public void givenANullKeyword_whenShowPatientListRequest_thenDisplayPatientList() throws Exception {
        when(patientProxy.getPatientList(null)).thenReturn(Arrays.asList(patient1DTO, patient2DTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/list"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(view().name("patient/list"))
                .andExpect(status().isOk());

        verify(patientProxy).getPatientList(null);
    }

    @Test
    @Tag("ShowPatientList")
    @DisplayName("Given a Keyword, when showPatientList request, then display expecting search Patient")
    public void givenAKeyword_whenShowPatientListRequest_thenDisplayExpectingSearchPatient() throws Exception {
        when(patientProxy.getPatientList("Durant")).thenReturn(Arrays.asList(patient1DTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/list/?keyword=Durant"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attributeExists("keyword"))
                .andExpect(view().name("patient/list"))
                .andExpect(status().isOk());

        verify(patientProxy).getPatientList("Durant");
    }

    @Test
    @Tag("AddPatientForm")
    @DisplayName("When addPatientForm request, then display Patient add form")
    public void whenAddPatientFormRequest_thenDisplayPatientAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/add"))
                .andExpect(model().attributeExists("patientDTO"))
                .andExpect(view().name("patient/add"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given a valid Patient to add, when validate request, then redirect to Patient list page")
    void givenAValidPatientToAdd_whenValidate_thenRedirectToPatientListPage() throws Exception {
        when(patientProxy.addPatient(any(PatientDTO.class))).thenReturn(any(PatientDTO.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/validate")
                .sessionAttr("patientDTO", patient1DTO)
                .param("lastName", patient1DTO.getLastName())
                .param("firstName", patient1DTO.getFirstName())
                .param("birthDate", patient1DTO.getBirthDate().toString())
                .param("sex", patient1DTO.getSex())
                .param("address", patient1DTO.getAddress())
                .param("phoneNumber", patient1DTO.getPhoneNumber()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/patient/list"));

        verify(patientProxy).addPatient(any(PatientDTO.class));
    }

    @Test
    @Tag("Validate")
    @DisplayName("Give empty LastName field, when validate request, then return error message and Patient add form")
    void givenAnEmptyPatientLastNameField_whenValidate_thenReturnErrorMessageAndPatientAddForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/patient/validate")
                .sessionAttr("patientDTO", patient1DTO)
                .param("lastName", "")
                .param("firstName", patient1DTO.getFirstName())
                .param("birthDate", patient1DTO.getBirthDate().toString())
                .param("sex", patient1DTO.getSex())
                .param("address", patient1DTO.getAddress())
                .param("phoneNumber", patient1DTO.getPhoneNumber()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("patient/add"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Last name is mandatory");
        verify(patientProxy, times(0)).addPatient(any(PatientDTO.class));
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given invalid sex value, when validate request, then return error message and Patient add form")
    void givenAnInvalidSexValue_whenValidate_thenReturnErrorMessageAndPatientAddForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/patient/validate")
                .sessionAttr("PatientDTO", patient1DTO)
                .param("lastName", patient1DTO.getLastName())
                .param("firstName", patient1DTO.getFirstName())
                .param("birthDate", patient1DTO.getBirthDate().toString())
                .param("sex", "L")
                .param("address", patient1DTO.getAddress())
                .param("phoneNumber", patient1DTO.getPhoneNumber()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("patient/add"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Please enter character M or F");
        verify(patientProxy, times(0)).addPatient(any(PatientDTO.class));
    }

    @Test
    @Tag("ShowUpdateForm")
    @DisplayName("When showUpdateForm request, then display Patient update form")
    public void whenShowUpdateFormRequest_thenDisplayPatientUpdateForm() throws Exception {
        when(patientProxy.getPatientById(1)).thenReturn(patient1DTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/update/1"))
                .andExpect(model().attributeExists("patientDTO"))
                .andExpect(view().name("patient/update"))
                .andExpect(status().isOk());

        verify(patientProxy).getPatientById(1);
    }

    @Test
    @Tag("UpdatePatient")
    @DisplayName("Given a valid Patient to update, when updatePatient request, then redirect to Patient list page")
    void givenAValidPatientToUpdate_whenUpdatePatient_thenRedirectToPatientListPage() throws Exception {
        PatientDTO patientToUpdateDTO = new PatientDTO("Durant", "Simon",
                LocalDate.of(1991,8,1), "M", "5 Warren Street", "77-888-9999");
        when(patientProxy.updatePatient(1, patient1DTO)).thenReturn(patientToUpdateDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update/1")
                .sessionAttr("patientDTO", patient1DTO)
                .param("lastName", patient1DTO.getLastName())
                .param("firstName", patient1DTO.getFirstName())
                .param("birthDate", patient1DTO.getBirthDate().toString())
                .param("sex", patient1DTO.getSex())
                .param("address", patient1DTO.getAddress())
                .param("phoneNumber", patient1DTO.getPhoneNumber()))
                .andExpect(redirectedUrl("/patient/list"));

        verify(patientProxy).updatePatient(anyInt(), any(PatientDTO.class));
    }

    @Test
    @Tag("UpdatePatient")
    @DisplayName("Given an invalid birthDate, when updatePatient request, then return error message and Patient update form")
    void givenAnInvalidBirthDate_whenUpdatePatient_thenReturnErrorMessageAndPatientUpdateForm() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/patient/update/1")
                .sessionAttr("patientDTO", patient1DTO)
                .param("lastName", patient1DTO.getLastName())
                .param("firstName", patient1DTO.getFirstName())
                .param("birthDate", LocalDate.of(9999,8,1).toString())
                .param("sex", patient1DTO.getSex())
                .param("address", patient1DTO.getAddress())
                .param("phoneNumber", patient1DTO.getPhoneNumber()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("patient/update"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Please enter a valid birth date");
        verify(patientProxy, times(0)).updatePatient(anyInt(), any(PatientDTO.class));
    }

    @Test
    @Tag("DeletePatient")
    @DisplayName("Given an Patient to delete, when delete request, then redirect to Patient list page")
    void givenAnPatientToDelete_whenDeletePatient_thenRedirectToPatientListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/delete/1"))
                .andExpect(redirectedUrl("/patient/list"));

        verify(patientProxy).deletePatient(1);
    }
}
