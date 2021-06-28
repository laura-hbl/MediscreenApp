package com.mediscreen.patientHistory.unit.util;

import com.mediscreen.patientHistory.dto.NoteDTO;
import com.mediscreen.patientHistory.model.Note;
import com.mediscreen.patientHistory.util.DTOConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class DTOConverterTest {

    private DTOConverter dtoConverter = new DTOConverter();

    @Test
    @DisplayName("Given an Note, when toNoteDTO, then result should match expected NoteDTO")
    public void givenAnPatient_whenToPatientDTO_thenReturnExpectedPatientDTO() {
        NoteDTO expectedNoteDTO = new NoteDTO("1", 1, LocalDate.of(2020,06,22),
                "my note");
        Note note = new Note("1", 1, LocalDate.of(2020,06,22), "my note");

        NoteDTO result = dtoConverter.toNoteDTO(note);

        assertThat(result).isEqualToComparingFieldByField(expectedNoteDTO);
    }
}
