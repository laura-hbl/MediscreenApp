package com.mediscreen.patient.integration;

import com.mediscreen.patient.dto.PatientDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource({"/application-test.properties"})
public class PatientControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // URL
    private final static String PATIENT_GET_URL = "/patient/get/";

    @Test
    @Tag("getPatientById")
    @DisplayName("Given an invalid id, when getPatientById request, then return BAD_REQUEST status")
    public void givenAnInvalidId_whenGetPatientByIdRequest_thenReturnOkStatus() {
        ResponseEntity<PatientDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                PATIENT_GET_URL + "a", PatientDTO.class);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("getPatientById")
    @DisplayName("Given an un found id, when getPatientById request, then return NOT FOUND status")
    public void givenAnUnFoundId_whenGetPatientByIdRequest_thenReturnNotFoundStatus() {
        ResponseEntity<PatientDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                PATIENT_GET_URL + "0", PatientDTO.class);

        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }

}
