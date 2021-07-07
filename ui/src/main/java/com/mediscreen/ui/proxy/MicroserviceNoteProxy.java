package com.mediscreen.ui.proxy;

import com.mediscreen.ui.dto.NoteDTO;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Permits to connection between ui application and patientHistory microservice.
 *
 * @author Laura Habdul
 */
@FeignClient(value = "note-microservice", url = "${proxy.note}")
public interface MicroserviceNoteProxy {

    /**
     * Adds a new note.
     *
     * @param noteDTO the note to be added
     * @return The added note
     */
    @PostMapping({"/add"})
    NoteDTO addNote(final NoteDTO noteDTO);

    /**
     * Retrieves a note based on the given id.
     *
     * @param noteId id of the note
     * @return The note with the given id
     */
    @GetMapping({"/get/{id}"})
    NoteDTO getNoteById(@PathVariable("id") final String noteId);

    /**
     * Updates a saved note.
     *
     * @param noteId  id of the note to be updated
     * @param noteDTO the note to be updated
     * @return The updated note
     */
    @PostMapping({"/update/{id}"})
    NoteDTO updateNote(@PathVariable("id") final String noteId, @Valid final NoteDTO noteDTO);

    /**
     * Retrieves the note list of patient with the given id.
     *
     * @param patientId id of the patient
     * @return The note list
     */
    @GetMapping({"/list/{id}"})
    List<NoteDTO> getAllNote(@PathVariable("id") final Integer patientId);

    /**
     * Deletes a saved note.
     *
     * @param noteId id of the note to be deleted
     */
    @GetMapping({"/delete/{id}"})
    void deleteNote(@PathVariable("id") final String noteId);
}