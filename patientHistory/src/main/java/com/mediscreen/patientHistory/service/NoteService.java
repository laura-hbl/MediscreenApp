package com.mediscreen.patientHistory.service;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.exception.ResourceNotFoundException;
import com.mediscreen.patientHistory.model.Note;
import com.mediscreen.patientHistory.repository.NoteRepository;
import com.mediscreen.patientHistory.util.DTOConverter;
import com.mediscreen.patientHistory.util.ModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService implements INoteService {

    private static final Logger LOGGER = LogManager.getLogger(NoteService.class);

    private final NoteRepository noteRepository;

    private final ModelConverter modelConverter;

    private final DTOConverter dtoConverter;

    @Autowired
    public NoteService(final NoteRepository noteRepository, final ModelConverter modelConverter,
                       final DTOConverter dtoConverter) {
        this.noteRepository = noteRepository;
        this.modelConverter = modelConverter;
        this.dtoConverter = dtoConverter;
    }

    public NoteDTO addNote(final NoteDTO noteDTO) {
        LOGGER.debug("Inside NoteService.addNote");

        Note noteAdded = noteRepository.save(modelConverter.toNote(noteDTO));

        return dtoConverter.toNoteDTO(noteAdded);
    }

    public NoteDTO getNoteById(final String noteId) {
        LOGGER.debug("Inside NoteService.getNoteById");

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("No note registered with this id"));

        return dtoConverter.toNoteDTO(note);
    }

    public NoteDTO updateNote(final String noteId, final NoteDTO noteDTO) {
        LOGGER.debug("Inside NoteService.updateNote");

        noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("No note registered with this id"));

        Note noteToUpdate = modelConverter.toNote(noteDTO);
        noteToUpdate.setId(noteId);
        Note noteUpdated = noteRepository.save(noteToUpdate);

        return dtoConverter.toNoteDTO(noteUpdated);
    }

    public List<NoteDTO> getAllNote(final Integer patientId) {
        LOGGER.debug("Inside NoteService.getAllNote");

        List<Note> notes = noteRepository.findByPatientId(patientId);

        List<NoteDTO> allNote = notes.stream()
                .map(note -> dtoConverter.toNoteDTO(note))
                .collect(Collectors.toList());

        return allNote;
    }

    public void deleteNote(final String noteId) {
        LOGGER.debug("Inside NoteService.deleteNote");

        noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("No note registered with this id"));

        noteRepository.deleteById(noteId);
    }
}
