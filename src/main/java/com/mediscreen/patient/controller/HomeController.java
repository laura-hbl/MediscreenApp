package com.mediscreen.patient.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Creates endpoint for Mediscreen app homepage access.
 *
 * @author Laura Habdul
 */
@Controller
public class HomeController {

    /**
     * HomeController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

    @GetMapping("/")
    public String showHomePage() {
        LOGGER.debug("GET Request on /");

        return "home";
    }
}
