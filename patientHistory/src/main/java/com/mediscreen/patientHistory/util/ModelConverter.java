package com.mediscreen.patientHistory.util;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.model.Note;
import org.springframework.stereotype.Component;

/**
 * Allows the conversion of DTO class to Model class.
 *
 * @author Laura Habdul
 */
@Component
public class ModelConverter {

    /**
     * Converts NoteDTO to Note.
     *
     * @param noteDTO NoteDTO object to convert
     * @return The Note object
     */
    public Note toNote(final NoteDTO noteDTO) {

        return new Note(noteDTO.getPatientId(), noteDTO.getDate(), noteDTO.getNote());
    }
}
