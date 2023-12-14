package com.example.hovedopgavekea.repository;

import com.example.hovedopgavekea.model.FieldOfStudy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldOfStudyRepository extends JpaRepository<FieldOfStudy, Long> {
    List<FieldOfStudy> findByFieldOfStudyName(String fieldOfStudyName);

}
