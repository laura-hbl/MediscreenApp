package com.mediscreen.patientAssessment.proxy;

import com.mediscreen.patientAssessment.dto.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Permits the connection between patientAssessment microservice and patientHistory microservice.
 *
 * @author Laura Habdul
 */
@FeignClient(value = "note-microservice", url = "${proxy.note}")
public interface MicroserviceNoteProxy {

    /**
     * Retrieves the note list of patient with the given id.
     *
     * @param patientId id of the patient
     * @return The note list
     */
    @GetMapping("/list/{id}")
    List<NoteDTO> getAllNote(@PathVariable("id") final Integer patientId);
}
