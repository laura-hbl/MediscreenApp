package com.mediscreen.patientAssessment.dto;

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
}

