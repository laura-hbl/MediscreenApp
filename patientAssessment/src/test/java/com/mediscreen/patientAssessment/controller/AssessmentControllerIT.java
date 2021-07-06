package com.mediscreen.patientAssessment.controller;

import com.mediscreen.patientAssessment.dto.AssessmentDTO;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssessmentControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    // URL
    private final static String ASSESS_GET_URL = "/assess/";

    @Test
    @Tag("getPatientAssessment")
    @DisplayName("Given an invalid id, when getPatientAssessment request, then return BAD_REQUEST status")
    public void givenAnInvalidId_whenGetPatientAssessmentRequest_thenReturnOkStatus() {
        ResponseEntity<AssessmentDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                ASSESS_GET_URL + "a", AssessmentDTO.class);

        assertEquals("request status", HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
    }

    @Test
    @Tag("getPatientAssessment")
    @DisplayName("Given an un found id, when getPatientAssessment request, then return NOT FOUND status")
    public void givenAnUnFoundId_whenGetPatientAssessmentRequest_thenReturnNotFoundStatus() {
        ResponseEntity<AssessmentDTO> response = restTemplate.getForEntity("http://localhost:" + port +
                ASSESS_GET_URL + "0", AssessmentDTO.class);

        assertEquals("request status", HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
    }
}
