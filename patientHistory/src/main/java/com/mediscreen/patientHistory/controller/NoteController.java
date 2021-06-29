package com.mediscreen.patientHistory.controller;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.service.INoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Creates REST endpoints for crud operations on Note data.
 *
 * @author Laura Habdul
 * @see INoteService
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    /**
     * NoteController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(NoteController.class);

    /**
     * INoteService's implement class reference.
     */
    private final INoteService notesService;

    /**
     * Constructor of class NoteController.
     * Initialize notesService.
     *
     * @param notesService INoteService's implement class reference
     */
    @Autowired
    public NoteController(final INoteService notesService) {
        this.notesService = notesService;
    }

    /**
     * Adds a new note.
     *
     * @param noteDTO the note to be added
     * @return The added note
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteDTO addNote(@RequestBody final NoteDTO noteDTO) {
        LOGGER.debug("POST Request on /note/add with patient id : {}", noteDTO.getPatientId());

        NoteDTO noteAdded = notesService.addNote(noteDTO);

        LOGGER.info("POST Request on /note/add - SUCCESS");
        return noteAdded;
    }

    /**
     * Retrieves a note based on the given id.
     *
     * @param noteId id of the note
     * @return The note with the given id
     */
    @GetMapping("/get/{id}")
    public NoteDTO getNoteById(@PathVariable("id") final String noteId) {
        LOGGER.debug("GET Request on /note/get/{id} with note id : {}", noteId);

        NoteDTO noteFound = notesService.getNoteById(noteId);

        LOGGER.info("GET Request on /note/get/{id} - SUCCESS");
        return noteFound;
    }

    /**
     * Updates a saved note.
     *
     * @param noteId  id of the note to be updated
     * @param noteDTO the note to be updated
     * @return The updated note
     */
    @PostMapping("/update/{id}")
    public NoteDTO updateNote(@PathVariable("id") final String noteId, @RequestBody final NoteDTO noteDTO) {
        LOGGER.debug("POST Request on /note/update/{id} with note id : {}", noteId);

        NoteDTO noteUpdated = notesService.updateNote(noteId, noteDTO);

        LOGGER.info("POST Request on /note/update/{id} - SUCCESS");
        return noteUpdated;
    }

    /**
     * Retrieves the note list of patient with the given id.
     *
     * @param patientId id of the patient
     * @return The note list
     */
    @GetMapping("/list/{id}")
    public List<NoteDTO> getNoteList(@PathVariable("id") final Integer patientId) {
        LOGGER.debug("GET Request on /note/list/{id} with patient id : {}", patientId);

        List<NoteDTO> allNote = notesService.getAllNote(patientId);

        LOGGER.info("GET Request on /note/list/{id} - SUCCESS");
        return allNote;
    }

    /**
     * Deletes a saved note.
     *
     * @param noteId id of the note to be deleted
     */
    @GetMapping("/delete/{id}")
    public void deleteNote(@PathVariable("id") final String noteId) {
        LOGGER.debug("GET Request on /note/delete/{id} with note id : {}", noteId);

        notesService.deleteNote(noteId);

        LOGGER.info("GET Request on /note/delete/{id} - SUCCESS");
    }
}
