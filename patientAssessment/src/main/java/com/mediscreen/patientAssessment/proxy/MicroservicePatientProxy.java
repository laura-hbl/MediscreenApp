package com.mediscreen.patientAssessment.proxy;

import com.mediscreen.patientAssessment.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "patient-microservice", url = "${proxy.patient}")
public interface MicroservicePatientProxy {

    @GetMapping("/get/{id}")
    PatientDTO getPatientById(@PathVariable("id") final Integer patientId);

}
