package com.mediscreen.patientHistory.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.patientHistory.controller.NoteController;
import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.service.INoteService;
import org.junit.jupiter.api.*;
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
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @MockBean
    private INoteService noteService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;

    // URL
    private final static String NOTE_GET_URL = "/note/get/";
    private final static String NOTE_DELETE_URL = "/note/delete/";
    private final static String NOTE_LIST_URL = "/note/list/";
    private final static String NOTE_UPDATE_URL = "/note/update/";
    private final static String NOTE_ADD_URL = "/note/add";

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Tag("getNoteById")
    @DisplayName("Given a note id, when getNoteById request, then return OK status")
    public void givenANoteId_whenGetNoteByIdRequest_thenReturnOKStatus() throws Exception {
        NoteDTO noteDTO = new NoteDTO("1", 1, LocalDate.of(2020,06,22), "my note");
        when(noteService.getNoteById("1")).thenReturn(noteDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(NOTE_GET_URL + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("my note");
        verify(noteService).getNoteById("1");
    }

    @Test
    @Tag("addNote")
    @DisplayName("Given a note to add, when addNote request, then return CREATED status")
    public void givenANoteToAdd_whenAddNoteRequest_thenReturnCREATEDStatus() throws Exception {
        NoteDTO noteDTO = new NoteDTO(1, LocalDate.of(2020,06,22), "my note");
        when(noteService.addNote(any(NoteDTO.class))).thenReturn(noteDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(NOTE_ADD_URL)
                .content(mapper.writeValueAsString(noteDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("my note");
        verify(noteService).addNote(any(NoteDTO.class));
    }

    @Test
    @Tag("updateNote")
    @DisplayName("Given a note to update, when updateNote request, then return Ok status")
    public void givenANoteToUpdate_whenUpdateNoteRequest_thenReturnOkStatus() throws Exception {
        NoteDTO noteDTO = new NoteDTO(1, LocalDate.of(2020,06,22), "my note updated");
        when(noteService.updateNote(anyString(), any(NoteDTO.class))).thenReturn(noteDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(NOTE_UPDATE_URL + 1)
                .content(mapper.writeValueAsString(noteDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("my note updated");
        verify(noteService).updateNote(anyString(), any(NoteDTO.class));
    }

    @Test
    @Tag("deleteNote")
    @DisplayName("Given a note id, when deleteNote request, then return OK status")
    public void givenANoteId_whenDeleteNoteRequest_thenReturnOKStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(NOTE_DELETE_URL + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(noteService).deleteNote("1");
    }

    @Test
    @Tag("getAllNote")
    @DisplayName("Given a patient id, when getAllNote request, then return OK status")
    public void givenAPatientId_whenGetAllNoteRequest_thenReturnOKStatus() throws Exception {
        NoteDTO note1DTO = new NoteDTO("1", 1, LocalDate.of(2020,06,22), "note 1");
        NoteDTO note2DTO = new NoteDTO("2", 1, LocalDate.of(2020,06,26), "note 2");
        when(noteService.getAllNote(1)).thenReturn(Arrays.asList(note1DTO, note2DTO));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(NOTE_LIST_URL + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        verify(noteService).getAllNote(1);
        assertThat(content).contains("note 1", "note 2");
    }
}
