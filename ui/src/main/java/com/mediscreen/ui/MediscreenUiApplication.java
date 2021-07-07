package com.mediscreen.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Launch Ui Application.
 *
 * @author Laura Habdul
 */
@SpringBootApplication
@EnableFeignClients({"com.mediscreen"})
public class MediscreenUiApplication {

    /**
     * Starts Ui application.
     *
     * @param args no argument
     */
    public static void main(final String[] args) {
        SpringApplication.run(MediscreenUiApplication.class, args);
    }
}
