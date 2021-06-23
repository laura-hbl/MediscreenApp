package com.mediscreen.patientHistory.service;

import com.mediscreen.patientHistory.dto.NoteDTO;

public interface INotesService {

    NoteDTO addNote(final NoteDTO note);

    NoteDTO getNoteById(final String noteId);

    NoteDTO updateNote(final String noteId, final NoteDTO noteDTO);
}
