package com.mediscreen.patientAssessment.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * Permits the storage and retrieving data of a note.
 *
 * @author Laura Habdul
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class NoteDTO {

    /**
     * Id of the note.
     */
    private String id;

    /**
     * Id of the patient.
     */
    private Integer patientId;

    /**
     * Date of the note.
     */
    private LocalDate date;

    /**
     * The note.
     */
    private String note;
}

