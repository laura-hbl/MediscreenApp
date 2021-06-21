package com.mediscreen.patientHistory.util;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.model.Note;
import org.springframework.stereotype.Component;

@Component
public class ModelConverter {

    public Note toNote(final NoteDTO noteDTO) {

        return new Note(noteDTO.getPatientId(), noteDTO.getDate(), noteDTO.getNote());
    }
}
