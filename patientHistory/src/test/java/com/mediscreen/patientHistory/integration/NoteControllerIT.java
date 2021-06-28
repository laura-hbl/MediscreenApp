package com.mediscreen.patientHistory.integration;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.service.NoteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource({"/application-test.properties"})
public class NoteControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NoteService noteService;

    @LocalServerPort
    private int port;

    // URL
    private final static String NOTE_GET_URL = "/note/get/";
    private final static String NOTE_DELETE_URL = "/note/delete/";
    private final static String NOTE_LIST_URL = "/note/list/";
    private final static String NOTE_UPDATE_URL = "/note/update/";
    private final static String NOTE_ADD_URL = "/note/add";


    @Test
    @Order(1)
    @Tag("addNote")
    @DisplayName("Given a Note, when addNote request, then note added and CREATED status should be returned")
    public void givenANoteToAdd_whenAddNoteRequest_thenReturnNoteAddedAndCreatedStatus() {
        NoteDTO noteToAdd = new NoteDTO(1, LocalDate.of(2020,06,22), "note test");

        ResponseEntity<NoteDTO> response = restTemplate.postForEntity("http://localhost:" + port + NOTE_ADD_URL,
                noteToAdd, NoteDTO.class);

        assertEquals("request status", HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .hasFieldOrPropertyWithValue("note", "note test")
                .isNotNull();
    }

    @Test
    @Order(2)
    @Tag("getNote")
    @DisplayName("Given a note id, when getNote request, then return OK status")
    public void givenANoteId_whenGetNoteByIdRequest_thenReturnOkStatus() {
        List<NoteDTO> notes = noteService.getAllNote(1);
        String id = notes.get(0).getId();

        ResponseEntity<NoteDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                NOTE_GET_URL + id, NoteDTO.class);

        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .hasFieldOrPropertyWithValue("note", "note test")
                .isNotNull();
    }

    @Test
    @Order(3)
    @Tag("getNote")
    @DisplayName("given invalid id, when getNote request, then return NOT FOUND status")
    public void givenInvalidId_whenGetNote_thenReturnNotFoundStatus() {
        ResponseEntity<NoteDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                NOTE_GET_URL + 4, NoteDTO.class);

        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

    @Test
    @Order(4)
    @Tag("getNoteList")
    @DisplayName("given a patient id, when getNoteList request, then return OK status")
    public void givenAPatientId_whenGetNoteListRequest_thenReturnOkStatus() {
        ResponseEntity<Object> response = restTemplate.getForEntity("http://localhost:" + port +
                NOTE_LIST_URL + 1, Object.class);

        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .asList()
                .hasSize(1)
                .asString()
                .contains("note test");
    }

    @Test
    @Order(5)
    @Tag("updateNote")
    @DisplayName("Given a Note, when updateNote request, then OK status should be returned")
    public void givenANoteToUpdate_whenUpdateNoteRequest_thenReturnPersonAddedAndCreatedStatus() {
        List<NoteDTO> notes = noteService.getAllNote(1);
        String id = notes.get(0).getId();
        NoteDTO noteToUpdate = new NoteDTO(1, LocalDate.of(2020,06,22), "note test updated");

        ResponseEntity<NoteDTO> response = restTemplate.postForEntity("http://localhost:" + port + NOTE_UPDATE_URL + id,
                noteToUpdate, NoteDTO.class);

        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(response.getBody())
                .hasFieldOrPropertyWithValue("note", "note test updated")
                .isNotNull();
    }

    @Test
    @Order(6)
    @Tag("deletePatient")
    @DisplayName("Given a note id, when deleteNote request, then return OK status")
    public void givenANoteId_whenDeleteNoteRequest_thenReturnOkStatus() {
        List<NoteDTO> notes = noteService.getAllNote(1);
        String id = notes.get(0).getId();
        ResponseEntity<Void> response = restTemplate.getForEntity("http://localhost:" + port +
                NOTE_DELETE_URL + id, Void.class);

        assertEquals("request status", HttpStatus.OK.value(), response.getStatusCodeValue());
    }
}
