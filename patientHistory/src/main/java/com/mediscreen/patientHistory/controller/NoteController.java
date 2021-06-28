package com.mediscreen.patientHistory.controller;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.service.INoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    private static final Logger LOGGER = LogManager.getLogger(NoteController.class);

    private final INoteService notesService;

    @Autowired
    public NoteController(final INoteService notesService) {
        this.notesService = notesService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDTO addNote(@RequestBody final NoteDTO noteDTO) {
        LOGGER.debug("POST Request on /note/add with patient id : {}", noteDTO.getPatientId());

        NoteDTO noteAdded = notesService.addNote(noteDTO);

        LOGGER.info("POST Request on /note/add - SUCCESS");
        return noteAdded;
    }

    @GetMapping("/get/{id}")
    public NoteDTO getNoteById(@PathVariable("id") final String noteId) {
        LOGGER.debug("GET Request on /note/get/{id} with note id : {}", noteId);

        NoteDTO noteFound = notesService.getNoteById(noteId);

        LOGGER.info("GET Request on /note/get/{id} - SUCCESS");
        return noteFound;
    }

    @PostMapping("/update/{id}")
    public NoteDTO updateNote(@PathVariable("id") final String noteId, @RequestBody final NoteDTO noteDTO) {
        LOGGER.debug("POST Request on /note/update/{id} with note id : {}", noteId);

        NoteDTO noteUpdated = notesService.updateNote(noteId, noteDTO);

        LOGGER.info("POST Request on /note/update/{id} - SUCCESS");
        return noteUpdated;
    }

    @GetMapping("/list/{id}")
    public List<NoteDTO> getNoteList(@PathVariable("id") final Integer patientId) {
        LOGGER.debug("GET Request on /note/list/{id} with patient id : {}", patientId);

        List<NoteDTO> allNote = notesService.getAllNote(patientId);

        LOGGER.info("GET Request on /note/list/{id} - SUCCESS");
        return allNote;
    }

    @GetMapping("/delete/{id}")
    public void deleteNote(@PathVariable("id") final String noteId) {
        LOGGER.debug("GET Request on /note/delete/{id} with note id : {}", noteId);

        notesService.deleteNote(noteId);

        LOGGER.info("GET Request on /note/delete/{id} - SUCCESS");
    }
}
