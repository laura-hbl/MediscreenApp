package com.mediscreen.patient.unit.util;

import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.model.Patient;
import com.mediscreen.patient.util.ModelConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelConverterTest {

    private ModelConverter modelConverter = new ModelConverter();

    @Test
    @DisplayName("Given an PatientDTO, when ToPatient, then result should match expected Patient")
    public void givenAPatientDTO_whenPatient_thenReturnExpectedPatient() {
        Patient expectedPatient = new Patient("Ferguson", "Lucas",
                LocalDate.of(1968,06,22), "M", "11 Warren Street", "387-866-1399");
        PatientDTO patientDTO = new PatientDTO("Ferguson", "Lucas",
                LocalDate.of(1968,06,22), "M", "11 Warren Street", "387-866-1399");

        Patient result = modelConverter.toPatient(patientDTO);

        assertThat(result).isEqualToComparingFieldByField(expectedPatient);
    }
}
