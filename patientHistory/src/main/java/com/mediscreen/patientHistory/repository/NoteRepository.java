package com.mediscreen.patientHistory.repository;

import com.mediscreen.patientHistory.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mongo Repository interface that provides methods that permit interaction with database notes collection.
 *
 * @author Laura Habdul
 */
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

    /**
     * Finds the list of note of the patient with the given id.
     *
     * @param patientId id of the patient
     * @return The patient note list
     */
    List<Note> findByPatientId(final Integer patientId);
}
