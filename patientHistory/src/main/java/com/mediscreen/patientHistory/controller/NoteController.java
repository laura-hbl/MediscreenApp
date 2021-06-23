package com.mediscreen.patientHistory.controller;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.service.INotesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
