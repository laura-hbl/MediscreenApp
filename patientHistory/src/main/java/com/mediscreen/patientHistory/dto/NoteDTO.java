package com.mediscreen.patientHistory.dto;

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

    /**
     * Constructor of class NoteDTO.
     * Initialize patientId, date and note.
     *
     * @param patientId id of the patient
     * @param date      date of the note
     * @param note      the note
     */
    public NoteDTO(final Integer patientId, final LocalDate date, final String note) {
        this.patientId = patientId;
        this.date = date;
        this.note = note;
    }
}
