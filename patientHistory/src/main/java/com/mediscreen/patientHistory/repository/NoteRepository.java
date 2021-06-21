package com.mediscreen.patientHistory.repository;

import com.mediscreen.patientHistory.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findByPatientId(final Integer patientId);
}
