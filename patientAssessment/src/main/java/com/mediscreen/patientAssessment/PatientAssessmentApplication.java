package com.mediscreen.patientAssessment;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.mediscreen")
public class PatientAssessmentApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PatientAssessmentApplication.class, args);
    }
}
