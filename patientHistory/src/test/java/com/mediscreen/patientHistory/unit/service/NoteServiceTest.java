package com.mediscreen.patientHistory.unit.service;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.exception.ResourceNotFoundException;
import com.mediscreen.patientHistory.model.Note;
import com.mediscreen.patientHistory.repository.NoteRepository;
import com.mediscreen.patientHistory.service.NoteService;
import com.mediscreen.patientHistory.util.DTOConverter;
import com.mediscreen.patientHistory.util.ModelConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteServiceTest {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private DTOConverter dtoConverter;

    @Mock
    private ModelConverter modelConverter;

    private static Note note1;

    private static Note note2;

    private static NoteDTO note1DTO;

    private static NoteDTO note2DTO;

    private static List<NoteDTO> noteListDTO;

    @Before
    public void setUp() {
        note1DTO = new NoteDTO("1", 1, LocalDate.of(2020,06,22), "note 1");
        note2DTO = new NoteDTO("2", 1, LocalDate.of(2020,06,29), "note 2");
        note1 = new Note("1",1, LocalDate.of(2020,06,22), "note 1");
        note2 = new Note("2",1, LocalDate.of(2020,06,29), "note 2");
        noteListDTO = Arrays.asList(note1DTO, note2DTO);
    }

    @Test
    @Tag("AddNote")
    @DisplayName("Given a note to add, when addNote, then Note should be saved correctly")
    public void givenANoteToAdd_whenAddNote_thenNoteShouldBeSavedCorrectly() {
        NoteDTO noteToAddDTO = new NoteDTO(1, LocalDate.of(2020,06,22), "note 1");
        Note noteToAdd = new Note(1, LocalDate.of(2020,06,22), "note 1");
        when(modelConverter.toNote(any(NoteDTO.class))).thenReturn(noteToAdd);
        when(noteRepository.save(any(Note.class))).thenReturn(note1);
        when(dtoConverter.toNoteDTO(any(Note.class))).thenReturn(note1DTO);

        NoteDTO noteSaved = noteService.addNote(noteToAddDTO);

        assertThat(noteSaved).isEqualToComparingFieldByField(note1DTO);
        InOrder inOrder = inOrder(noteRepository, modelConverter, dtoConverter);
        inOrder.verify(modelConverter).toNote(any(NoteDTO.class));
        inOrder.verify(noteRepository).save(any(Note.class));
        inOrder.verify(dtoConverter).toNoteDTO(any(Note.class));
    }

    @Test
    @Tag("UpdateNote")
    @DisplayName("Given a registered Note, when updateNote, then Note should be updated correctly")
    public void givenARegisteredNote_whenUpdateNote_thenNoteShouldBeUpdateCorrectly() {

        Note noteUpdated = new Note("1", 1, LocalDate.of(2020,06,22), "note updated");
        NoteDTO noteUpdatedDTO = new NoteDTO("1", 1, LocalDate.of(2020,06,22), "note updated");
        when(noteRepository.findById("1")).thenReturn(java.util.Optional.ofNullable(note1));
        when(modelConverter.toNote(any(NoteDTO.class))).thenReturn(new Note(1,
                LocalDate.of(2020,06,22), "note 1 updated"));
        when(noteRepository.save(any(Note.class))).thenReturn(noteUpdated);
        when(dtoConverter.toNoteDTO(any(Note.class))).thenReturn(noteUpdatedDTO);

        NoteDTO result = noteService.updateNote("1", new NoteDTO(1,
                LocalDate.of(2020,06,22), "note 1 updated"));

        assertThat(result).isEqualTo(noteUpdatedDTO);
        InOrder inOrder = inOrder(noteRepository, modelConverter, dtoConverter);
        inOrder.verify(noteRepository).findById("1");
        inOrder.verify(modelConverter).toNote(any(NoteDTO.class));
        inOrder.verify(noteRepository).save(any(Note.class));
        inOrder.verify(dtoConverter).toNoteDTO(any(Note.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdateNote")
    @DisplayName("If Note cant be found, when updateNote, then throw ResourceNotFoundException")
    public void givenUnFoundNote_whenUpdateNote_thenResourceNotFoundExceptionIsThrown() {
        when(noteRepository.findById("1")).thenReturn(java.util.Optional.empty());

        noteService.updateNote("1", note1DTO);
    }

    @Test
    @Tag("DeleteNote")
    @DisplayName("Given Note Id, when deleteNote, then delete process should be done in correct order")
    public void givenANoteId_whenDeleteNote_thenDeletingShouldBeDoneInCorrectOrder() {
        when(noteRepository.findById("1")).thenReturn(java.util.Optional.ofNullable(note1));

        noteService.deleteNote("1");

        InOrder inOrder = inOrder(noteRepository);
        inOrder.verify(noteRepository).findById("1");
        inOrder.verify(noteRepository).deleteById("1");
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteNote")
    @DisplayName("If Note can't be found, when deleteNote, then throw ResourceNotFoundException")
    public void givenUnFoundNote_whenDeleteNote_thenResourceNotFoundExceptionIsThrown() {
        when(noteRepository.findById("1")).thenReturn(java.util.Optional.empty());

        noteService.deleteNote("1");
    }

    @Test
    @Tag("GetNoteById")
    @DisplayName("Given an Note id, when getNoteById, then expected Note should be returned correctly")
    public void givenAnNoteId_whenGetNoteById_thenExpectedNoteShouldBeReturnCorrectly() {
        when(noteRepository.findById("1")).thenReturn(java.util.Optional.ofNullable(note1));
        when(dtoConverter.toNoteDTO(any(Note.class))).thenReturn(note1DTO);

        NoteDTO noteFound = noteService.getNoteById("1");

        assertThat(noteFound).isEqualToComparingFieldByField(note1DTO);

        InOrder inOrder = inOrder(noteRepository, dtoConverter);
        inOrder.verify(noteRepository).findById("1");
        inOrder.verify(dtoConverter).toNoteDTO(any(Note.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("GetNoteById")
    @DisplayName("If Note can't be found, when getNoteById, then throw ResourceNotFoundException")
    public void givenUnFoundNote_whenGetNoteById_thenResourceNotFoundExceptionIsThrown() {
        when(noteRepository.findById("1")).thenReturn(java.util.Optional.empty());

        noteService.getNoteById("1");
    }

    @Test
    @Tag("GetAllNote")
    @DisplayName("When getAllNote, then result should match expected Note list")
    public void whenGetAllNote_thenReturnAllNoteList() {
        when(noteRepository.findByPatientId(1)).thenReturn(Arrays.asList(note1, note2));
        when(dtoConverter.toNoteDTO(note1)).thenReturn(note1DTO);
        when(dtoConverter.toNoteDTO(note2)).thenReturn(note2DTO);

        List<NoteDTO> result = noteService.getAllNote(1);

        assertThat(result).isEqualTo(noteListDTO);
        InOrder inOrder = inOrder(noteRepository, dtoConverter);
        inOrder.verify(noteRepository).findByPatientId(1);
        inOrder.verify(dtoConverter).toNoteDTO(note1);
        inOrder.verify(dtoConverter).toNoteDTO(note2);
    }
}
