package com.mediscreen.patientHistory.service;

import com.mediscreen.patientHistory.dto.NoteDTO;

import java.util.List;

public interface INoteService {

    NoteDTO addNote(final NoteDTO note);

    NoteDTO getNoteById(final String noteId);

    NoteDTO updateNote(final String noteId, final NoteDTO noteDTO);

    List<NoteDTO> getAllNote(final Integer patientId);

    void deleteNote(final String noteId);
}
