package com.mediscreen.patientAssessment.config;

import com.mediscreen.patientAssessment.exception.FeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * PatientAssessment application configuration class.
 *
 * @author Laura Habdul
 */
@Configuration
public class Beans {

    /**
     * Creates a FeignErrorDecoder bean.
     *
     * @return the FeignErrorDecoder instance
     */
    @Bean
    public FeignErrorDecoder myErrorDecoder() {
        return new FeignErrorDecoder();
    }
}
