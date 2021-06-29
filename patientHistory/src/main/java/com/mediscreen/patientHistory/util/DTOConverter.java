package com.mediscreen.patientHistory.util;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.model.Note;
import org.springframework.stereotype.Component;

/**
 * Allows the conversion of Model class to DTO class.
 *
 * @author Laura Habdul
 */
@Component
public class DTOConverter {

    /**
     * Converts Note to NoteDTO.
     *
     * @param note Note object to convert
     * @return The NoteDTO object
     */
    public NoteDTO toNoteDTO(final Note note) {

        return new NoteDTO(note.getId(), note.getPatientId(), note.getDate(), note.getNote());
    }
}
