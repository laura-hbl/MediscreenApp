package com.mediscreen.patientAssessment;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Launch PatientAssessment Application.
 *
 * @author Laura Habdul
 */
@SpringBootApplication
@EnableFeignClients("com.mediscreen")
public class PatientAssessmentApplication {

    /**
     * Starts PatientAssessment application.
     *
     * @param args no argument
     */
    public static void main(final String[] args) {
        SpringApplication.run(PatientAssessmentApplication.class, args);
    }
}
