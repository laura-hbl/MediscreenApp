package com.mediscreen.patientAssessment.proxy;

import com.mediscreen.patientAssessment.dto.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(value = "note-microservice", url = "${proxy.note}")
public interface MicroserviceNoteProxy {

    @GetMapping("/list/{id}")
    List<NoteDTO> getAllNote(@PathVariable("id") final Integer patientId);
}
