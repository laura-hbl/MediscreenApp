package com.mediscreen.patientAssessment.service;

import com.mediscreen.patientAssessment.dto.AssessmentDTO;
import com.mediscreen.patientAssessment.dto.NoteDTO;
import com.mediscreen.patientAssessment.dto.PatientDTO;
import com.mediscreen.patientAssessment.enums.DiabetesTrigger;
import com.mediscreen.patientAssessment.enums.RiskLevels;
import com.mediscreen.patientAssessment.proxy.MicroserviceNoteProxy;
import com.mediscreen.patientAssessment.proxy.MicroservicePatientProxy;
import com.mediscreen.patientAssessment.util.AgeCalculator;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Contains methods that deals about patient diabetes assessment business logic.
 *
 * @author Laura Habdul
 */
@Service
public class AssessmentService implements IAssessmentService {

    /**
     * AssessmentService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AssessmentService.class);

    /**
     * MicroserviceNoteProxy instance.
     */
    private final MicroserviceNoteProxy noteProxy;

    /**
     * MicroservicePatientProxy instance.
     */
    private final MicroservicePatientProxy patientProxy;

    /**
     * AgeCalculator instance.
     */
    private final AgeCalculator ageCalculator;

    /**
     * Constructor of class AssessmentService.
     * Initialize noteProxy, patientProxy and ageCalculator.
     *
     * @param noteProxy     MicroserviceNoteProxy instance
     * @param patientProxy  MicroservicePatientProxy instance
     * @param ageCalculator AgeCalculator instance
     */
    @Autowired
    public AssessmentService(final MicroserviceNoteProxy noteProxy, final MicroservicePatientProxy patientProxy,
                             final AgeCalculator ageCalculator) {
        this.noteProxy = noteProxy;
        this.patientProxy = patientProxy;
        this.ageCalculator = ageCalculator;
    }

    /**
     * Calls MicroserviceNoteProxy's getAllNote to retrieves all notes of the patient with the given id, then
     * find the number of diabetes triggers in those notes by calling getPatientTriggers method. Calls
     * MicroservicePatientProxy's getPatientById to retrieves the patient with the given id and calculate his age
     * by calling AgeCalculator's getAge method. Finally with the patient age, sex and triggers numbers, gets
     * the patient diabetes risk level by calling getDiabetesRiskLevel method.
     *
     * @param patientId id of the patient
     * @return AssessmentDTO object that contains the patient data and his diabetes risk level
     */
    public AssessmentDTO getPatientAssessment(final Integer patientId) {
        LOGGER.debug("Inside AssessmentService.getPatientAssessment");

        List<NoteDTO> patientNotes = noteProxy.getAllNote(patientId);
        int patientTriggers = getPatientTriggers(patientNotes);

        PatientDTO patient = patientProxy.getPatientById(patientId);
        int patientAge = ageCalculator.getAge(patient.getBirthDate());

        String diabetesRiskLevel = getDiabetesRiskLevel(patientTriggers, patientAge, patient.getSex());

        return new AssessmentDTO(patient, diabetesRiskLevel);

    }

    /**
     * Determines the patient's risk level for diabetes based on age, gender, and the number of diabetes triggers.
     *
     * @param triggers   the number of diabetes triggers the patient has
     * @param patientAge age of the patient
     * @param sex        gender of the patient
     * @return The patient's risk level for diabetes
     */
    private String getDiabetesRiskLevel(final int triggers, final int patientAge, final String sex) {
        LOGGER.debug("Inside AssessmentService.getDiabetesRiskLevel");

        String diabetesAssessment = RiskLevels.NONE.getRiskLevel();

        if ((((sex.equals("F")) && patientAge < 30 && triggers >= 7) || ((sex.equals("M")) && patientAge < 30
                && triggers >= 5)) || (patientAge >= 30 && triggers >= 8)) {
            diabetesAssessment = RiskLevels.EARLY_ONSET.getRiskLevel();
        } else if ((sex.equals("M") && patientAge < 30 && triggers >= 3) || ((sex.equals("F")) && patientAge < 30
                && triggers >= 4) || (patientAge >= 30 && triggers >= 6)) {
            diabetesAssessment = RiskLevels.IN_DANGER.getRiskLevel();
        } else if ((patientAge >= 30 && triggers >= 2)) {
            diabetesAssessment = RiskLevels.BORDERLINE.getRiskLevel();
        }

        return diabetesAssessment;
    }

    /**
     * Determines the number of diabetes triggers contained in patient notes.
     *
     * @param notes note list of the patient
     * @return The numbers of diabetes triggers the patient has
     */
    private int getPatientTriggers(final List<NoteDTO> notes) {
        LOGGER.debug("Inside AssessmentService.getPatientTriggers");

        EnumSet<DiabetesTrigger> diabetesTriggers = EnumSet.allOf(DiabetesTrigger.class);

        List<DiabetesTrigger> patientTriggers = new ArrayList();
        diabetesTriggers.forEach(diabetesTrigger -> {
            notes.forEach(note -> {
                if (StringUtils.containsIgnoreCase(note.getNote(), diabetesTrigger.getTrigger()) &&
                        !patientTriggers.contains(diabetesTrigger)) {
                    patientTriggers.add(diabetesTrigger);
                }
            });
        });

        return patientTriggers.size();
    }
}
