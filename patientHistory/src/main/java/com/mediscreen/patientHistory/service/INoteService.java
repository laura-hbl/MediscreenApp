package com.mediscreen.patientHistory.service;

import com.mediscreen.patientHistory.dto.NoteDTO;

import java.util.List;

/**
 * NoteService interface.
 *
 * @author Laura Habdul
 */
public interface INoteService {

    /**
     * Registers a new note in database.
     *
     * @param note the note to be registered
     * @return The note saved converted to an NoteDTO object
     */
    NoteDTO addNote(final NoteDTO note);

    /**
     * Retrieves a note based on the given id.
     *
     * @param noteId id of the note to be found
     * @return The note retrieved converted to an NoteDTO object
     */
    NoteDTO getNoteById(final String noteId);

    /**
     * Updates a note based on the given id.
     *
     * @param noteId  id of the note to be updated
     * @param noteDTO the note to be updated
     * @return The note updated converted to an NoteDTO object
     */
    NoteDTO updateNote(final String noteId, final NoteDTO noteDTO);

    /**
     * Retrieves all the note list of the patient with the given id.
     *
     * @param patientId id of the patient
     * @return The note list
     */
    List<NoteDTO> getAllNote(final Integer patientId);

    /**
     * Deletes a note based on the given id.
     *
     * @param noteId id of the note to be deleted
     */
    void deleteNote(final String noteId);
}
