package com.mediscreen.ui.proxy;

import com.mediscreen.ui.dto.AssessmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Permits to connection between ui application and patientAssessment microservice.
 *
 * @author Laura Habdul
 */
@FeignClient(value = "assess-microservice", url = "${proxy.assess}")
public interface MicroserviceAssessProxy {

    /**
     * Retrieves the diabetes assessment of the patient with the given id.
     *
     * @param patientId id of the patient
     * @return The patient diabetes assessment
     */
    @GetMapping({"/{id}"})
    AssessmentDTO getPatientAssessment(@PathVariable("id") final Integer patientId);
}
