package com.mediscreen.patientHistory.util;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.model.Note;
import org.springframework.stereotype.Component;

@Component
public class DTOConverter {

    public NoteDTO toNoteDTO(final Note note) {

        return new NoteDTO(note.getId(), note.getPatientId(), note.getDate(), note.getNote());
    }
}
