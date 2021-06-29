package com.mediscreen.patientHistory.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(collection = "notes")
public class Note {

    /**
     * Id of the note.
     */
    @Id
    private String id;

    /**
     * Id of the patient.
     */
    @Field(value = "patient_id")
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
    public Note(final Integer patientId, final LocalDate date, final String note) {
        this.patientId = patientId;
        this.date = date;
        this.note = note;
    }
}
