package com.mediscreen.patientHistory.service;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.model.Note;
import com.mediscreen.patientHistory.repository.NoteRepository;
import com.mediscreen.patientHistory.util.DTOConverter;
import com.mediscreen.patientHistory.util.ModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotesService implements INotesService {

    private static final Logger LOGGER = LogManager.getLogger(NotesService.class);

    private final NoteRepository noteRepository;

    private final ModelConverter modelConverter;

    private final DTOConverter dtoConverter;

    @Autowired
    public NotesService(final NoteRepository noteRepository, final ModelConverter modelConverter,
                        final DTOConverter dtoConverter) {
        this.noteRepository = noteRepository;
        this.modelConverter = modelConverter;
        this.dtoConverter = dtoConverter;
    }

    public NoteDTO addNote(final NoteDTO noteDTO) {
        LOGGER.debug("Inside NotesService.addNote");

        Note noteAdded = noteRepository.save(modelConverter.toNote(noteDTO));

        return dtoConverter.toNoteDTO(noteAdded);
    }
}
