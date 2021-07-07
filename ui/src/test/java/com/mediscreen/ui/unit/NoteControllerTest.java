package com.mediscreen.ui.unit;

import com.mediscreen.ui.controller.NoteController;
import com.mediscreen.ui.dto.NoteDTO;
import com.mediscreen.ui.proxy.MicroserviceNoteProxy;
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
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @MockBean
    private MicroserviceNoteProxy noteProxy;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private NoteDTO note1DTO;

    private NoteDTO note2DTO;

    @BeforeEach
    public void setUp() {
        note1DTO = new NoteDTO(1, LocalDate.of(2020,06,22), "note 1");
        note2DTO = new NoteDTO(1, LocalDate.of(2020,07,11), "note 2");

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("showNoteList")
    @DisplayName("Given a patient id, when showNoteList request, then display Note list")
    public void givenAPatientId_whenShowNoteListRequest_thenDisplayNoteList() throws Exception {
        when(noteProxy.getAllNote(1)).thenReturn(Arrays.asList(note1DTO, note2DTO));
        mockMvc.perform(MockMvcRequestBuilders.get("/note/list/1"))
                .andExpect(model().attributeExists("patientId"))
                .andExpect(model().attributeExists("notes"))
                .andExpect(view().name("note/list"))
                .andExpect(status().isOk());

        verify(noteProxy).getAllNote(1);
    }

    @Test
    @Tag("AddNoteForm")
    @DisplayName("When addNoteForm request, then display Note add form")
    public void whenAddNoteFormRequest_thenDisplayPatientNoteAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/note/add/1"))
                .andExpect(model().attributeExists("noteDTO"))
                .andExpect(view().name("note/add"))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("Validate")
    @DisplayName("Given a valid Note to add, when validate request, then redirect to Note list page")
    void givenANoteToAdd_whenValidate_thenRedirectToNoteListPage() throws Exception {
        when(noteProxy.addNote(any(NoteDTO.class))).thenReturn(note1DTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/note/validate")
                .sessionAttr("noteDTO", note1DTO)
                .param("patientId", note1DTO.getPatientId().toString())
                .param("date", note1DTO.getDate().toString())
                .param("note", note1DTO.getNote()))
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/note/list/1"));

        verify(noteProxy).addNote(any(NoteDTO.class));
    }

    @Test
    @Tag("ShowUpdateForm")
    @DisplayName("When showUpdateForm request, then display Note update form")
    public void whenShowUpdateFormRequest_thenDisplayPatientUpdateForm() throws Exception {
        when(noteProxy.getNoteById("1")).thenReturn(note1DTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/note/update/1"))
                .andExpect(model().attributeExists("noteDTO"))
                .andExpect(view().name("note/update"))
                .andExpect(status().isOk());

        verify(noteProxy).getNoteById("1");
    }

    @Test
    @Tag("UpdateNote")
    @DisplayName("Given a Note to update, when updateNote request, then redirect to Note list page")
    void givenANoteToUpdate_whenUpdateNote_thenRedirectToNoteListPage() throws Exception {
        when(noteProxy.updateNote(anyString(), any(NoteDTO.class))).thenReturn(note1DTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/note/update/1")
                .sessionAttr("noteDTO", note1DTO)
                .param("patientId", note1DTO.getPatientId().toString())
                .param("date", note1DTO.getDate().toString())
                .param("note", note1DTO.getNote()))
                .andExpect(redirectedUrl("/note/list/1"));

        verify(noteProxy).updateNote(anyString(), any(NoteDTO.class));
    }

    @Test
    @Tag("DeleteNote")
    @DisplayName("Given an Note to delete, when delete request, then redirect to Note list page")
    void givenANoteToDelete_whenDeleteNote_thenRedirectToNoteListPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/note/delete/1/1"))
                .andExpect(redirectedUrl("/note/list/1"));

        verify(noteProxy).deleteNote("1");
    }
}