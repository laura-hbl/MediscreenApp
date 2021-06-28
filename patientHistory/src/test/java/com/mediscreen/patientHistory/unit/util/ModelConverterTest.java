package com.mediscreen.patientHistory.unit.util;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.model.Note;
import com.mediscreen.patientHistory.util.ModelConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelConverterTest {

    private ModelConverter modelConverter = new ModelConverter();

    @Test
    @DisplayName("Given an NoteDTO, when toNote, then result should match expected Note")
    public void givenAnPatient_whenToPatientDTO_thenReturnExpectedPatientDTO() {
        Note expectedNote = new Note(1, LocalDate.of(2020,06,22),
                "my note");
        NoteDTO noteDTO = new NoteDTO("1", 1, LocalDate.of(2020,06,22), "my note");

        Note result = modelConverter.toNote(noteDTO);

        assertThat(result).isEqualToComparingFieldByField(expectedNote);
    }
}
