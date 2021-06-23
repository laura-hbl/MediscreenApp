package com.mediscreen.patientHistory.service;

import com.mediscreen.patientHistory.dto.NoteDTO;

public interface INotesService {

    NoteDTO addNote(final NoteDTO note);

}
