package com.mediscreen.patient.unit.util;

import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.util.DTOConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class DTOConverterTest {

    private DTOConverter dtoConverter = new DTOConverter();

    @Test
    @DisplayName("Given an Patient, when ToPatientDTO, then result should match expected PatientDTO")
    public void givenAnPatient_whenToPatientDTO_thenReturnExpectedPatientDTO() {
        PatientDTO expectedPatientDTO = new PatientDTO(1, "Ferguson", "Lucas",
                LocalDate.of(1968,06,22), "M", "11 Warren Street", "387-866-1399");
        Patient Patient = new Patient(1, "Ferguson", "Lucas",
                LocalDate.of(1968,06,22), "M", "11 Warren Street", "387-866-1399");

        PatientDTO result = dtoConverter.toPatientDTO(Patient);

        assertThat(result).isEqualToComparingFieldByField(expectedPatientDTO);
    }
}
