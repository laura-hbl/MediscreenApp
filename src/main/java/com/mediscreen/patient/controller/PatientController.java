package com.mediscreen.patient.controller;

import com.mediscreen.patient.dto.PatientDTO;
import com.mediscreen.patient.service.IPatientService;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private static final Logger LOGGER = LogManager.getLogger(PatientController.class);

    private final IPatientService patientService;

    @Autowired
    public PatientController(final IPatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/add")
    public String addPatientForm(final Model model) {
        LOGGER.debug("GET Request on /patient/add");

        model.addAttribute("patientDTO", new PatientDTO());

        LOGGER.info("GET Request on /patient/add - SUCCESS");
        return "patient/add";
    }

    @PostMapping("/validate")
    public String validate(@Valid final PatientDTO patientDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /patient/validate on patient {} {}", patientDTO.getFirstName() + "" +
                patientDTO.getLastName());

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "patient/add";
        }
        patientService.addPatient(patientDTO);

        LOGGER.info("POST Request on /patient/validate  - SUCCESS");
        return "redirect:/patient/list";
    }
}
