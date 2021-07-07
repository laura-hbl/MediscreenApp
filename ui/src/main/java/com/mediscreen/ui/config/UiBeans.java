package com.mediscreen.ui.config;

import com.mediscreen.ui.exception.FeignErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ui application configuration class.
 *
 * @author Laura Habdul
 */
@Configuration
public class UiBeans {

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