package com.mediscreen.patientHistory.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class NoteDTO {

    private String id;

    private Integer patientId;

    private LocalDate date;

    private String note;

    public NoteDTO(final Integer patientId, final LocalDate date, final String note) {
        this.patientId = patientId;
        this.date = date;
        this.note = note;
    }
}
