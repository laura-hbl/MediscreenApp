package com.mediscreen.patientHistory.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Document(collection = "notes")
public class Note {

    @Id
    private String id;

    @Field(value = "patient_id")
    private Integer patientId;

    private LocalDate date;

    private String note;

    public Note(final Integer patientId, final LocalDate date, final String note) {
        this.patientId = patientId;
        this.date = date;
        this.note = note;
    }
}
