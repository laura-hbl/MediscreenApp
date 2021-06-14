package com.mediscreen.patient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Launch Patient Application.
 *
 * @author Laura Habdul
 */
@SpringBootApplication
public class PatientApplication {

	/**
	 * Starts Patient application.
	 *
	 * @param args no argument
	 */
	public static void main(final String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}
}
