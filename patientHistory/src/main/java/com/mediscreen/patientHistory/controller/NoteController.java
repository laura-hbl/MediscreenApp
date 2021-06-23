package com.mediscreen.patientHistory.controller;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.service.INotesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
public class NoteController {

    private static final Logger LOGGER = LogManager.getLogger(NoteController.class);

    private final INotesService notesService;

    @Autowired
    public NoteController(final INotesService notesService) {
        this.notesService = notesService;
    }


    @PostMapping("/add")
    public NoteDTO addNote(@RequestBody final NoteDTO noteDTO) {
        LOGGER.debug("POST Request on /note/add with patient id : {}", noteDTO.getPatientId());

        NoteDTO noteAdded = notesService.addNote(noteDTO);

        LOGGER.info("POST Request on /note/add - SUCCESS");
        return noteAdded;
    }

    @GetMapping("/update/{id}")
    public NoteDTO getNoteById(@PathVariable("id") final String noteId) {
        LOGGER.debug("GET Request on /note/update/{id} with note id : {}", noteId);

        NoteDTO noteFound = notesService.getNoteById(noteId);

        LOGGER.info("GET Request on /note/update/{id} - SUCCESS");
        return noteFound;
    }

    @PostMapping("/update/{id}")
    public NoteDTO updateNote(@PathVariable("id") final String noteId, @RequestBody final NoteDTO noteDTO) {
        LOGGER.debug("POST Request on /note/update/{id} with note id : {}", noteId);

        NoteDTO noteUpdated = notesService.updateNote(noteId, noteDTO);

        LOGGER.info("POST Request on /note/update/{id} - SUCCESS");
        return noteUpdated;
    }
}
