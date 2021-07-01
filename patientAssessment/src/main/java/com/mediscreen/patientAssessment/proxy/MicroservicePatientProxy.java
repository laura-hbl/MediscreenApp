package com.mediscreen.patientAssessment.proxy;

import com.mediscreen.patientAssessment.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Permits the connection between patientAssessment microservice and patient microservice.
 *
 * @author Laura Habdul
 */
@FeignClient(value = "patient-microservice", url = "${proxy.patient}")
public interface MicroservicePatientProxy {

    /**
     * Retrieves a patient based on the given id.
     *
     * @param patientId id of the patient
     * @return The patient with the given id
     */
    @GetMapping("/get/{id}")
    PatientDTO getPatientById(@PathVariable("id") final Integer patientId);
}
