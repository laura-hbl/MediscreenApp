package com.mediscreen.ui.controller;

import com.mediscreen.ui.dto.NoteDTO;
import com.mediscreen.ui.proxy.MicroserviceNoteProxy;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Creates endpoints for crud operations on Note data.
 *
 * @author Laura Habdul
 */
@Controller
@RequestMapping({"/note"})
public class NoteController {

    /**
     * NoteController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(NoteController.class);

    /**
     * MicroserviceNoteProxy's implement class reference.
     */
    private final MicroserviceNoteProxy noteProxy;

    /**
     * Constructor of class NoteController.
     * Initialize noteProxy.
     *
     * @param noteProxy MicroserviceNoteProxy's implement class reference
     */
    @Autowired
    public NoteController(final MicroserviceNoteProxy noteProxy) {
        this.noteProxy = noteProxy;
    }

    /**
     * Displays form for adding a new note.
     *
     * @param patientId id of the patient for whom the note must be added
     * @param model     makes a new NoteDTO object available to note/add HTML page
     * @return The reference to the note/add HTML page
     */
    @GetMapping({"/add/{id}"})
    public String addNoteForm(@PathVariable("id") final Integer patientId, final Model model) {
        LOGGER.debug("GET Request on /note/add/{id} with patient id : {}", patientId);

        NoteDTO note = new NoteDTO();
        note.setPatientId(patientId);
        model.addAttribute("noteDTO", note);

        LOGGER.info("GET Request on /note/add/{id} - SUCCESS");
        return "note/add";
    }

    /**
     * Adds a new note.
     *
     * @param noteDTO the note to be added
     * @param result  holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the note/add HTML page if result has errors. Else, redirects to /note/list endpoint
     */
    @PostMapping({"/validate"})
    public String validate(@Valid final NoteDTO noteDTO, final BindingResult result) {
        LOGGER.debug("POST Request on /note/validate");

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "note/add";
        } else {
            this.noteProxy.addNote(noteDTO);

            LOGGER.info("POST Request on /note/validate - SUCCESS");
            return "redirect:/note/list/" + noteDTO.getPatientId();
        }
    }

    /**
     * Displays a form to update a note.
     *
     * @param noteId id of the note to be updated
     * @param model  makes NoteDTO object available to note/update HTML page
     * @return The reference to the note/update HTML page
     */
    @GetMapping({"/update/{id}"})
    public String showUpdateForm(@PathVariable("id") final String noteId, final Model model) {
        LOGGER.debug("GET Request on /note/update/{id} with note id : {}", noteId);

        NoteDTO note = this.noteProxy.getNoteById(noteId);
        model.addAttribute("noteDTO", note);

        LOGGER.info("GET Request on /note/update/{id} - SUCCESS");
        return "note/update";
    }

    /**
     * Updates a saved note.
     *
     * @param noteId  id of the note to be updated
     * @param noteDTO the note to be updated
     * @param result  holds the result of validation and binding and contains errors that may have occurred
     * @return The reference to the note/update HTML page if result has errors. Else, redirects to /note/list endpoint
     */
    @PostMapping({"/update/{id}"})
    public String updateNote(@PathVariable("id") final String noteId, @Valid final NoteDTO noteDTO,
                             final BindingResult result) {
        LOGGER.debug("POST Request on /note/update/{id} with note id : {}", noteId);

        if (result.hasErrors()) {
            LOGGER.error("Error(s): {}", result);
            return "note/update";
        } else {
            this.noteProxy.updateNote(noteId, noteDTO);

            LOGGER.info("POST Request on /note/update/{id} - SUCCESS");
            return "redirect:/note/list/" + noteDTO.getPatientId();
        }
    }

    /**
     * Displays the note list of patient with the given id.
     *
     * @param model     makes note list objects available to note/list HTML page
     * @param patientId patientId id of the patient
     * @return The reference to the note/list HTML page
     */
    @GetMapping({"/list/{id}"})
    public String showNoteList(@PathVariable("id") final Integer patientId, final Model model) {
        LOGGER.debug("GET Request on note/list/{id} with patient id : {}", patientId);

        model.addAttribute("patientId", patientId);
        model.addAttribute("notes", this.noteProxy.getAllNote(patientId));

        LOGGER.info("GET Request on /note/list/{id} - SUCCESS");
        return "note/list";
    }

    /**
     * Deletes a saved note.
     *
     * @param noteId    id of the note to be deleted
     * @param patientId id of the patient whose note to be deleted
     * @return The redirect to /note/list endpoint
     */
    @GetMapping({"/delete/{id}/{patientId}"})
    public String deleteNote(@PathVariable("id") final String noteId, @PathVariable("patientId") final Integer patientId) {

        LOGGER.debug("GET Request on note/delete/{id} with note id : {}", noteId);
        this.noteProxy.deleteNote(noteId);

        LOGGER.info("GET Request on /note/delete/{id} - SUCCESS");
        return "redirect:/note/list/" + patientId;
    }
}

